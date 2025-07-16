package com.example.SportingEventTracker.Event;
import com.example.SportingEventTracker.Game.Game;
import com.example.SportingEventTracker.Game.GameRepository;
import com.example.SportingEventTracker.dto.EventDTO;
import com.example.SportingEventTracker.dto.EventRequestDTO;
import com.example.SportingEventTracker.dto.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final GameRepository gameRepository;
    @Autowired
    public EventService(EventRepository eventRepository, GameRepository gameRepository) {
        this.eventRepository = eventRepository;
        this.gameRepository = gameRepository;

    }

    /*business logic for if a user wants to add an event to their event table
    -finds and saves the game (using gameId) that the frontend dto sends to the backend from the correct game repo
    -checks to see if the game already exists within event table for that specific user
    -creates a new event entity and adds its fields using the setters and the parameters from the request dto
    -saves this to the eventRepository (which stores all events that have been saved) using JPA built-in function
     */
    public void addEvent(EventRequestDTO eventReq) {
        Game game = gameRepository.findById(eventReq.getGameId()).orElseThrow();
        boolean alreadyExists = eventRepository.findByUserIdAndGame(eventReq.getUserId(), game) != null;
        if (alreadyExists) {
            throw new RuntimeException("Event already exists for this user and game");
        }
        Event event = new Event();
        event.setUserId(eventReq.getUserId());
        event.setUserNote(eventReq.getUserNote());
        event.setGame(game);
        eventRepository.save(event);
    }



   /*business logic for when a user wants to delete an event from their event table
   -finds and saves the game (using gameId) that the frontend dto sends to the backend from the correct game repo
   -uses this saved game as well as userID (from event request dto) to find specific event in events table, saves it as temp variable
   -deletes this from the eventRepository (which stores all events that have been saved) using JPA built-in function
    */

    public void deleteEvent(EventRequestDTO eventReq) {
        Game game = gameRepository.findById(eventReq.getGameId()).orElseThrow();
        Event event = eventRepository.findByUserIdAndGame(eventReq.getUserId(), game);;
        eventRepository.delete(event);
    }


    /*business logic for when a user wants to update an event's notes from their event table
       -finds and saves the game (using gameId) that the frontend dto sends to the backend from the correct game repo
       -uses this saved game as well as userID (from event request dto) to find specific event in events table, saves it as temp
       -changes this temp variable's userNote using event's setter (and getter from event request)
       -replaces event from the eventRepository with new event using JPA built-in function
        */
    public void editEventNote(EventRequestDTO eventReq) {
        Game game = gameRepository.findById(eventReq.getGameId()).orElseThrow();
        Event event = eventRepository.findByUserIdAndGame(eventReq.getUserId(), game);
        if (event == null) throw new RuntimeException("Event not found for update");
        event.setUserNote(eventReq.getUserNote());
        eventRepository.save(event);
    }




    /*business logic for getting the events based on userid and custom filters
    -uses conditionals to see what filters are selected by the user in the event request dto
    -uses the helper function that calls the correct eventrepo based on what filters are present
    -finally, returns all events that a user attended if no filters are selected
    -userID can't be null!!
    -doesn't use game_id here because the user is not filtering by game_id
     */
    public List<EventDTO> getEvents(EventRequestDTO eventReq) {
        if((eventReq.getYear() != null) && (eventReq.getSportType() != null)) {
            return getAllEventsByUserIDAndYearAndSport(eventReq);
        }
        else if((eventReq.getYear() != null)) {
            return getAllEventsByUserIDAndYear(eventReq);
        }
        else if((eventReq.getSportType() != null)) {
            return getAllEventsByUserIDAndSport(eventReq);
        }
        else{
            return getAllEventsByUserID(eventReq);
        }
    }


    //helper methods
    private List<EventDTO> getAllEventsByUserID(EventRequestDTO eventReq) {
        return eventRepository.findByUserId(eventReq.getUserId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<EventDTO> getAllEventsByUserIDAndSport(EventRequestDTO eventReq) {
        return eventRepository.findByUserIdAndSportType(eventReq.getUserId(), eventReq.getSportType()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<EventDTO> getAllEventsByUserIDAndYear(EventRequestDTO eventReq) {
        return eventRepository.findByUserIdAndYear(eventReq.getUserId(), eventReq.getYear()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<EventDTO> getAllEventsByUserIDAndYearAndSport(EventRequestDTO eventReq) {

        return eventRepository
                .findByUserIdAndYearAndSport(eventReq.getUserId(), eventReq.getYear(), eventReq.getSportType())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EventDTO convertToDto(Event event) {
        EventDTO dto = new EventDTO();
        dto.setEventId(event.getId());
        dto.setUserNote(event.getUserNote());
        dto.setGame(GameMapper.toDTO(event.getGame()));
        return dto;}
}
