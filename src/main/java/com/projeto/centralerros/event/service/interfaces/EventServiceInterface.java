package com.projeto.centralerros.event.service.interfaces;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface EventServiceInterface {

    Optional<Event> createUpdateLevel(@NotNull EventLevel level, String description, String log, String origin);

    Page<Event> findAllParams(EventLevel level, String log, String description,
                              String origin, String eventDate, Integer quantity, Pageable pageable);

    List<Event> findAll(Example<Event> events, Pageable pageable);

    //List<Event> getErrors(Example<Event> eventExample, Pageable pageable);

}
