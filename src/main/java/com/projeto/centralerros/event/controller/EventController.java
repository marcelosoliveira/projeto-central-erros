package com.projeto.centralerros.event.controller;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.dto.EventLogDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.impl.EventService;
import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Entity;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping
@AllArgsConstructor
public class EventController {

    private EventRepository eventRepository;

    private EventService eventService;

    private ModelMapper modelMapper;

    @GetMapping("/errors")
    public Page<EventDTO> findAll(Pageable pageable) {
        return this.eventRepository.findAllTest(pageable).map(this::toEventDTO);
    }

    @GetMapping(value = "errors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findById(@PathVariable(value = "id") Long id)
            throws ResponseException {
        try {
            Optional<EventLogDTO> log = this.eventRepository.findByIdLog(id).map(this::toEventLogDTO);
            if (log.isEmpty()) {
                throw new ResponseException("Evento não encontrado!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(log);
        } catch (ResponseException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\":\"" + r.getMessage() + "\" }");
        }
    }

    @PostMapping("/central")
    public Object createErrors(@RequestBody Event event) throws Exception {
        try {
            Optional<Event> eventDto = Optional.ofNullable(this.eventService.createUpdateLevel(event));
            if (eventDto.get().getOrigin().isEmpty()) {
                throw new Exception("Origin não estar vazio");
            }
            return ResponseEntity.status(HttpStatus.OK).body(eventDto.map(this::toEventDTO));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("{\"message\":\"" + e.getMessage()+ "\" }");
        }
    }

    @GetMapping("/central")
    public Page<EventDTO> findByErrors(@RequestParam(value = "level", required = false) EventLevel level,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "log", required = false) String log,
                                       @RequestParam(value = "origin", required = false) String origin,
                                       @RequestParam(value = "eventDate", required = false) LocalDateTime eventDate,
                                       @RequestParam(value = "quantity", required = false) Integer quantity,
                                       Pageable pageable) {

        return this.eventService.findAllParams(level, description, log, origin,
                eventDate, quantity, pageable).map(this::toEventDTO);
    }

    private EventDTO toEventDTO(Event event) {
        return this.modelMapper.map(event, EventDTO.class);
    }

    private EventLogDTO toEventLogDTO(Event event) {
        return this.modelMapper.map(event, EventLogDTO.class);
    }

}
