package br.com.centralerrors.event.controller;

import br.com.centralerrors.dto.EventDTO;
import br.com.centralerrors.dto.EventLogDTO;
import br.com.centralerrors.enums.EventLevel;
import br.com.centralerrors.event.model.Event;
import br.com.centralerrors.event.repository.EventRepository;
import br.com.centralerrors.event.service.impl.EventService;
import br.com.centralerrors.exceptions.ResponseNotFoundException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private EventRepository eventRepository;

    private EventService eventService;

    private ModelMapper modelMapper;

    @GetMapping(path = "/events/all")
    @ApiOperation(value = "Lista todos eventos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina a ser carregada"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Quantidade de registros"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Ordenacao dos registros")
    })
    public ResponseEntity<Page<EventDTO>> findAll(@PageableDefault(
            sort = "quantity", direction = Sort.Direction.DESC, page = 0, size = 100)
                                                      @ApiIgnore Pageable pageable) {
        Page<EventDTO> eventDTOPage = this.eventService.findAll(pageable).map(this::toEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(eventDTOPage);
    }

    @GetMapping(path = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta evento pelo id exibindo o log")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Evento não encontrado")
    })
    public ResponseEntity<Optional<EventLogDTO>> findById(@PathVariable(value = "id") UUID id) {
        verifyEventId(id);
        Optional<EventLogDTO> eventLogDTO = this.eventService.findByIdLog(id).map(this::toEventLogDTO);
        return ResponseEntity.status(HttpStatus.OK).body(eventLogDTO);
    }

    @PostMapping(path = "/events")
    @ApiOperation(value = "Cadastra um evento de erros")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Evento criado")
    })
    public ResponseEntity<Optional<EventDTO>> createEvents(@Valid @RequestBody Event event) {
        Optional<Event> eventDto = Optional.ofNullable(this.eventService.createUpdateEvent(event));
        return ResponseEntity.status(HttpStatus.CREATED).body(eventDto.map(this::toEventDTO));
    }

    @GetMapping(path = "/events")
    @ApiOperation(value = "Lista eventos de acordo com os parâmetros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina a ser carregada"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Quantidade de registros"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Ordenacao dos registros")
    })
    public ResponseEntity<Page<EventDTO>> findByEventParams(
                                       @RequestParam(value = "level", required = false) EventLevel level,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "log", required = false) String log,
                                       @RequestParam(value = "origin", required = false) String origin,
                                       @RequestParam(value = "eventDate", required = false) String date,
                                       @RequestParam(value = "quantity", required = false) Integer quantity,
                                       @PageableDefault(
                                               sort = "quantity", direction = Sort.Direction.DESC, page = 0, size = 100)
                                       @ApiIgnore Pageable pageable) {

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

    private void verifyEventId(UUID id) {
        if (!this.eventService.findByIdLog(id).isPresent()) {
            throw new ResponseNotFoundException("Evento não encontrado id: " + id);
        }
    }

}
