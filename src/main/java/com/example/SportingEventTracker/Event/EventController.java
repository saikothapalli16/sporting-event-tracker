package com.example.SportingEventTracker.Event;



import com.example.SportingEventTracker.dto.EventDTO;
import com.example.SportingEventTracker.dto.EventRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/event")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDTO> getEvents(@RequestParam Long userId,
                                    @RequestParam(required = false) Integer year,
                                    @RequestParam(required = false) String sport) {
        EventRequestDTO eventRequest = new EventRequestDTO();
        eventRequest.setUserId(userId);
        eventRequest.setSportType(sport);
        eventRequest.setYear(year);
        ResponseEntity.ok();
        return eventService.getEvents(eventRequest);
    }

    @PostMapping
    public ResponseEntity<String> addEvent(@RequestBody EventRequestDTO eventRequest) {
        eventService.addEvent(eventRequest);
        return ResponseEntity.ok("Event added successfully");
    }

    @DeleteMapping
    public ResponseEntity<String>  deleteEvent(@RequestBody EventRequestDTO eventRequest) {
        eventService.deleteEvent(eventRequest);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateEvent(@RequestBody EventRequestDTO eventRequest) {
        eventService.editEventNote(eventRequest);
        return ResponseEntity.ok("Note updated");
    }
}
