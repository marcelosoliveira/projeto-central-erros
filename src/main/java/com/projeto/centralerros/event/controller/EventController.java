package com.projeto.centralerros.event.controller;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.impl.EventService;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class EventController {

    private EventRepository eventRepository;

    private EventService eventService;

    @GetMapping("/errors")
    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    @PostMapping("/central")
    public Optional<Event> captureError(@RequestParam(value = "level") @NotNull EventLevel level,
                                        @RequestParam(value = "description") String description,
                                        @RequestParam(value = "log") String log,
                                        @RequestParam(value = "origin") String origin) {

        return this.eventService.createUpdateLevel(level, description, log, origin);
    }

    @GetMapping("/central")
    public List<Event> requestErrors(@RequestParam(value = "level", required = false) @NotNull EventLevel level,
                                         @RequestParam(value = "description", required = false) String description,
                                         @RequestParam(value = "log", required = false) String log,
                                         @RequestParam(value = "origin", required = false) String origin,
                                         @RequestParam(value = "eventDate", required = false) String eventDate,
                                         @RequestParam(value = "quantity", required = false) String quantity,
                                         Pageable pageable) {
        LocalDateTime dateEvent = LocalDateTime.parse(eventDate);
        Integer quantityInteger = Integer.parseInt(quantity);
        return this.eventService.getErrors(level, description, log, origin, dateEvent, quantityInteger);
    }

}
