package com.projeto.centralerros.event.controller;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.dto.EventLogDTO;
import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.impl.EventService;
import com.projeto.centralerros.exceptions.ResponseNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private EventRepository eventRepository;

    private EventService eventService;

    private ModelMapper modelMapper;

    @GetMapping(path = "/events/all")
    public ResponseEntity<Page<EventDTO>> findAll(Pageable pageable) {
        Page<EventDTO> eventDTOPage = this.eventService.findAll(pageable).map(this::toEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(eventDTOPage);
    }

    @GetMapping(path = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        //verifyEventId(id);
        Optional<EventLogDTO> eventLogDTO = this.eventService.findByIdLog(id).map(this::toEventLogDTO);
        return ResponseEntity.status(HttpStatus.OK).body(eventLogDTO);
    }

    @PostMapping(path = "/events")
    public ResponseEntity<?> createEvents(@Valid @RequestBody Event event) {
        Optional<Event> eventDto = Optional.ofNullable(this.eventService.createUpdateEvent(event));
        return ResponseEntity.status(HttpStatus.OK).body(eventDto.map(this::toEventDTO));
    }

    @GetMapping(path = "/events")
    public ResponseEntity<Page<EventDTO>> findByEventParams(
                                       @RequestParam(value = "level", required = false) EventLevel level,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "log", required = false) String log,
                                       @RequestParam(value = "origin", required = false) String origin,
                                       @RequestParam(value = "eventDate", required = false) String date,
                                       @RequestParam(value = "quantity", required = false) Integer quantity,
                                       Pageable pageable) {

        Page<EventDTO> eventDto = this.eventService.findByParams(level, description, log, origin,
                date, quantity, pageable).map(this::toEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(eventDto);
    }

    private EventDTO toEventDTO(Event event) {
        return this.modelMapper.map(event, EventDTO.class);
    }

    private EventLogDTO toEventLogDTO(Event event) {
        return this.modelMapper.map(event, EventLogDTO.class);
    }

   /* private void verifyEventId(Long id) {
        if (this.eventService.findByIdLog(id).isEmpty()) {
            throw new ResponseNotFoundException("Evento não encontrado id: " + id);
        }
    }*/

}
