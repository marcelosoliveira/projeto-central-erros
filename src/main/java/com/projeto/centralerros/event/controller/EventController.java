package com.projeto.centralerros.event.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.impl.EventService;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.map.util.JSONWrappedObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.util.List;
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

    @GetMapping(value = "errors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findById(@PathVariable(value = "id") Long id) {
        String log = this.eventRepository.findByIdLog(id);
        if (log == null) {
            return "Error: Id not exist!";
        }
        return "{\"id\":\"" + id + "\", \"log\":\"" + log + "\"}";
        //return this.eventRepository.findByIdLog(id);
    }

    @PostMapping("/central")
    public Optional<Event> captureError(@RequestParam(value = "level") EventLevel level,
                                        @RequestParam(value = "description") String description,
                                        @RequestParam(value = "log") String log,
                                        @RequestParam(value = "origin") String origin) {

        return this.eventService.createUpdateLevel(level, description, log, origin);
    }

    @GetMapping("/central")
    public Page<EventDTO> findByErrors(@RequestParam(value = "level", required = false) EventLevel level,
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
