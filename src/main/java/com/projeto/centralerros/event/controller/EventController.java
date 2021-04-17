package com.projeto.centralerros.event.controller;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.dto.LogDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.impl.EventService;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@AllArgsConstructor
public class EventController {

    private EventRepository eventRepository;

    private EventService eventService;

    private ModelMapper modelMapper;

    @GetMapping("/errors")
    public Page<EventDTO> findAll(Pageable pageable) {
        return new PageImpl<>(this.eventRepository.findAll(pageable)
                .stream().map(this::toEventDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "errors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<LogDTO> findById(@PathVariable(value = "id") Long id) {
        return this.eventRepository.findByIdLog(id).map(this::toLogDTO);
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

        return new PageImpl<>(this.eventService.findAllParams(
                level, description, log, origin, eventDate, quantity, pageable)
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

    private EventDTO toEventDTO(Event event) {
        return this.modelMapper.map(event, EventDTO.class);
    }

    private LogDTO toLogDTO(Event event) {
        return this.modelMapper.map(event, LogDTO.class);
    }

}
