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

    Page<Event> findByLevelOrLogOrDescriptionOrOriginOrEventDateOrQuantity(
            EventLevel level,
            String log,
            String description                                                           ,
            String origin,
            String eventDate,
            Integer quantity,
            Pageable pageable);

    Optional<Event> findByLog(String log);


    @Query(value = "UPDATE events SET quantity = :quantity WHERE log = :log", nativeQuery = true)
    Optional<Event> updateByQuantity(@Param("log") String log,
                                     @Param("quantity") Integer quantity);

}
