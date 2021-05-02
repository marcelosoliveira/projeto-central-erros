package br.com.centralerrors.event.service.interfaces;

import br.com.centralerrors.event.model.Event;
import br.com.centralerrors.enums.EventLevel;
import br.com.centralerrors.exceptions.ResponseNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventServiceInterface {

    Event createUpdateEvent(Event event) throws ResponseNotFoundException;

    Page<Event> findByParams(EventLevel level, String description, String log,
                             String origin, String date, Integer quantity, Pageable pageable);

    Page<Event> findAll(Pageable pageable);

    Optional<Event> findByIdLog(UUID id);

}
