package com.projeto.centralerros.event.controller;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.impl.EventService;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class EventController {

    private EventRepository eventRepository;

    private EventService eventService;

    @GetMapping("/errors")
    public Page<Event> findAll(Pageable pageable) {
        return this.eventRepository.findAll(pageable);
    }

    @GetMapping("errors/{id}")
    public Optional<String> findById(@PathVariable(value = "id") Long id) {
        return this.eventRepository.findByIdLog(id);
    }

    @PostMapping("/central")
    public Optional<Event> captureError(@RequestParam(value = "level") EventLevel level,
                                        @RequestParam(value = "description") String description,
                                        @RequestParam(value = "log") String log,
                                        @RequestParam(value = "origin") String origin) {

        return this.eventService.createUpdateLevel(level, description, log, origin);
    }

    @GetMapping("/central")
    public Page<Event> findByErrors(@RequestParam(value = "level", required = false) EventLevel level,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "log", required = false) String log,
                                       @RequestParam(value = "origin", required = false) String origin,
                                       @RequestParam(value = "eventDate", required = false) String eventDate,
                                       @RequestParam(value = "quantity", required = false) Integer quantity,
                                       Pageable pageable) {
       return this.eventService.findAllParams(
               level, description, log, origin, eventDate, quantity, pageable);
    }

}
