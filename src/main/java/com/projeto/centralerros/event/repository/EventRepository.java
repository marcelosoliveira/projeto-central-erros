package com.projeto.centralerros.event.repository;

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

    Page<Event> findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
            EventLevel level,
            String description,
            String log,
            String origin,
            String eventDate,
            Integer quantity,
            Pageable pageable);

    Optional<Event> findByLevelAndLogAndDescriptionAndOrigin(
            EventLevel level,
            String log,
            String description,
            String origin);

    Optional<Event> findByLog(String log);

    @Query(value = "UPDATE events SET quantity = :quantity, event_date = :eventDate" +
            " WHERE level = :level AND log = :log AND description = :description" +
            " AND origin = :origin", nativeQuery = true)
    Optional<Event> updateByQuantity(@Param("level") String level,
                                     @Param("log") String log,
                                     @Param("description") String description,
                                     @Param("origin") String origin,
                                     @Param("eventDate") String eventDate,
                                     @Param("quantity") Integer quantity);

    @Query(value = "SELECT * FROM events WHERE id = :id", nativeQuery = true)
    Optional<Event> findByIdLog(/*@Param("id")*/ Long id);
}
