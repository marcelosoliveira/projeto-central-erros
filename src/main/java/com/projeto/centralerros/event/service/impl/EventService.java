package com.projeto.centralerros.event.service.impl;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.event.repository.EventRepository;
import com.projeto.centralerros.event.service.interfaces.EventServiceInterface;
import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.secutiry.LoginSecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {

    private EventRepository eventRepository;

    private LoginSecurityUser loginSecurityUser;

    @Override
    public Event createUpdateLevel(Event event) {
        Long idUser = this.loginSecurityUser.getLoginUser().getId();
        String level = event.getLevel().toString();

        Optional<Event> eventFound = this.eventRepository.findTest(
                level, event.getLog(), event.getDescription(), event.getOrigin(), idUser);

      if (eventFound.isPresent()) {
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
    public Page<Event> findAllParams(EventLevel level, String description, String log,
                       String origin, LocalDateTime eventDate, Integer quantity, Pageable pageable) {

        return this.eventRepository.findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
                level, description, log, origin, eventDate, quantity, pageable);
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        Long idUser = this.loginSecurityUser.getLoginUser().getId();
        return this.eventRepository.findAll(idUser, pageable);
    }

    @Override
    public Optional<Event> findByIdLog(Long id) {
        Long idUser = this.loginSecurityUser.getLoginUser().getId();
        return this.eventRepository.findByIdLog(id, idUser);
    }

}
