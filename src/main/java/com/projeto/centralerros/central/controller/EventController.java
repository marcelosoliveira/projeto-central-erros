package com.projeto.centralerros.central.controller;

import com.projeto.centralerros.central.model.Event;
import com.projeto.centralerros.central.repository.EventRepository;
import com.projeto.centralerros.central.service.impl.EventService;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
    public Optional<Event> captureError(@RequestParam(value = "level", required = true) @NotNull EventLevel level,
                                       @RequestParam(value = "description", required = true) String description,
                                       @RequestParam(value = "log", required = true) String log,
                                       @RequestParam(value = "origin", required = true) String origin) {

        return this.eventService.levelCondition(level, description, log, origin);
    }

    @GetMapping("/central")
    public Optional<Event> requestErrors(@RequestParam(value = "level", required = true) @NotNull EventLevel level,
                                        @RequestParam(value = "description", required = true) String description,
                                        @RequestParam(value = "log", required = true) String log,
                                        @RequestParam(value = "origin", required = true) String origin) {

        return this.eventService.levelCondition(level, description, log, origin);
    }

}
