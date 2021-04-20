package com.projeto.centralerros.event.service.impl;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.interfaces.EventServiceInterface;
import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.secutiry.LoginSecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {

    private EventRepository eventRepository;

    private LoginSecurityUser loginSecurityUser;

    @Override
    public Event createUpdateLevel(Event event) {
        Optional<Event> eventFound = this.eventRepository.findByLevelAndLogAndDescriptionAndOrigin(
                event.getLevel(), event.getLog(), event.getDescription(), event.getOrigin());

        //String levelString = String.valueOf(event.getLevel());

        if (eventFound.isPresent()) {
           /*this.eventRepository.updateByQuantity(levelString, eventFound.get().getLog(),
                    eventFound.get().getDescription(), eventFound.get().getOrigin(),
                        LocalDateTime.now(), eventFound.get().getQuantity() + 1);*/
            eventFound.get().setQuantity(eventFound.get().getQuantity() + 1);
            eventFound.get().setEventDate(LocalDateTime.now());
            eventFound.get().getUsers().add(this.loginSecurityUser.getLoginUser());
            return this.eventRepository.save(eventFound.get());
        };

        event.setQuantity(1);
        event.getUsers().add(this.loginSecurityUser.getLoginUser());
        return this.eventRepository.save(event);
    }

    @Override
    public List<Event> findAll(Example<Event> events, Pageable pageable) {
        return this.eventRepository.findAll(events, pageable).getContent();
    }

    @Override
    public Page<Event> findAllParams(EventLevel level, String description, String log,
                       String origin, LocalDateTime eventDate, Integer quantity, Pageable pageable) {

        return this.eventRepository.findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
                level, description, log, origin, eventDate, quantity, pageable);
    }

    @Override
    public Boolean isNotValid(Event event) {
        EventLevel[] level = { EventLevel.ERROR, EventLevel.INFO, EventLevel.WARNING };
        if(event.getOrigin().isEmpty() || event.getDescription().isEmpty() ||
                event.getLog().isEmpty() || !event.getLevel().equals(EventLevel.ERROR)
        || !event.getLevel().equals(EventLevel.INFO) || !event.getLevel().equals(EventLevel.WARNING)){
            return true;
        }
        return false;
    }

}
