package com.projeto.centralerros.event.service.interfaces;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.exceptions.ResponseNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventServiceInterface {

    Event createUpdateEvent(Event event) throws ResponseNotFoundException;

    Page<Event> findByParams(EventLevel level, String description, String log,
                             String origin, String date, Integer quantity, Pageable pageable);

    Page<Event> findAll(Pageable pageable);

    Optional<Event> findByIdLog(Long id);

}
