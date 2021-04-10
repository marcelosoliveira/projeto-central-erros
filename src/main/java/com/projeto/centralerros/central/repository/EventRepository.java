package com.projeto.centralerros.central.repository;

import com.projeto.centralerros.central.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByLevelAndDescriptionAndLogAndOrigin(EventLevel level,
                                                             String description                                                           ,
                                                             String Log,
                                                             String origin);
}
