package com.projeto.centralerros.event.service.impl;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.interfaces.EventServiceInterface;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {

    private EventRepository eventRepository;

    @Override
    public Optional<Event> createUpdateLevel(@NotNull EventLevel level, String description, String log, String origin) {
        Event event = new Event();
        event.setQuantity(1);
        event.setLevel(level);
        event.setDescription(description);
        event.setLog(log);
        event.setOrigin(origin);

        if (this.eventRepository.findByLog(log).isPresent()) {
            this.eventRepository.findByLog(log).stream().forEach(event1 -> {
                this.eventRepository.updateByQuantity(log, event1.getQuantity() + 1);
            });
        }

        this.eventRepository.save(event);

        return this.eventRepository.findByLog(log);
    }

    @Override
    public List<Event> getErrors(@NotNull EventLevel level,
                                 String description                                                           ,
                                 String Log,
                                 String origin,
                                 LocalDateTime eventDate,
                                 Integer quantity) {
        return this.eventRepository
                .findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
                        level,
                        description                                                           ,
                        Log,
                        origin,
                        eventDate,
                        quantity);
    }

}
