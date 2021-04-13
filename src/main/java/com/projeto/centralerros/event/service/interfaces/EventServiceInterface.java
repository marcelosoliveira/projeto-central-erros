package com.projeto.centralerros.event.service.interfaces;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventServiceInterface {

    Optional<Event> createUpdateLevel(@NotNull EventLevel level, String description, String log, String origin);

    List<Event> getErrors(@NotNull EventLevel level,
                          String description                                                           ,
                          String Log,
                          String origin,
                          LocalDateTime eventDate,
                          Integer quantity);

    //List<Event> getErrors(Example<Event> eventExample, Pageable pageable);

}
