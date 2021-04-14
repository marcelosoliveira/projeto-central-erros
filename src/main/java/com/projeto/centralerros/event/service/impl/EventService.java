package com.projeto.centralerros.event.service.impl;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.interfaces.EventServiceInterface;
import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {

    private EventRepository eventRepository;

    @Override
    public Optional<Event> createUpdateLevel(EventLevel level, String description, String log, String origin) {
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
    public List<Event> findAll(Example<Event> events, Pageable pageable) {
        return this.eventRepository.findAll(events, pageable).getContent();
    }

    @Override
    public Page<Event> findAllParams(EventLevel level, String log, String description,
                                     String origin, String eventDate, Integer quantity, Pageable pageable){
        return this.eventRepository.findByLevelOrLogOrDescriptionOrOriginOrEventDateOrQuantity(
                level, log, description, origin, eventDate, quantity, pageable);
    }

}
