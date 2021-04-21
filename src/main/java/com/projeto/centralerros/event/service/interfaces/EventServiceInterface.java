package com.projeto.centralerros.event.service.interfaces;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.exceptions.ResponseNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;

public interface EventServiceInterface {

    Event createUpdateLevel(Event event) throws ResponseNotFoundException;

    Page<Event> findAllParams(EventLevel level, String description, String log,
                              String origin, LocalDateTime eventDate, Integer quantity, Pageable pageable);

    Page<Event> findAll(Pageable pageable);

    Optional<Event> findByIdLog(Long id);

}
