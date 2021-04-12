package com.projeto.centralerros.central.service.impl;

import com.projeto.centralerros.central.model.Event;
import com.projeto.centralerros.central.repository.EventRepository;
import com.projeto.centralerros.central.service.interfaces.EventServiceInterface;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {

    private EventRepository eventRepository;

    @Override
    public Optional<Event> levelCondition(@NotNull EventLevel level, String description, String log, String origin) {
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

            return this.eventRepository.findByLevel(level);
        }

        this.eventRepository.save(event);

        return this.eventRepository.findByLevelAndDescriptionAndLogAndOrigin(
                event.getLevel(), event.getDescription(), event.getLog(), event.getOrigin());
    }

}
