package com.projeto.centralerros.event.repository;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<EventDTO> findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
            EventLevel level,
            String description,
            String log,
            String origin,
            String eventDate,
            Integer quantity,
            Pageable pageable);

    Optional<Event> findByLevelAndLogAndDescription(
            EventLevel level,
            String log,
            String description);

    Optional<Event> findByLog(String log);


    @Query(value = "UPDATE events SET quantity = :quantity WHERE log = :log", nativeQuery = true)
    Optional<Event> updateByQuantity(@Param("log") String log,
                                     @Param("quantity") Integer quantity);

    @Query(value = "SELECT id, level, description, origin, event_date, quantity FROM events" +
            " WHERE level = :level OR log = :log OR description = :description OR " +
            "origin = :origin OR event_date = :eventDate OR quantity = :quantity", nativeQuery = true)
    Page<Event> findEvents(
            @Param("level") EventLevel level,
            @Param("log") String log,
            @Param("description") String description                                                           ,
            @Param("origin") String origin,
            @Param("eventDate") String eventDate,
            @Param("quantity") Integer quantity,
            Pageable pageable);

    @Query(value = "SELECT log FROM events WHERE id = :id", nativeQuery = true)
    String findByIdLog(@Param("id") Long id);
}
