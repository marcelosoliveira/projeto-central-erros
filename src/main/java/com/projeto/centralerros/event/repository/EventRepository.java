package com.projeto.centralerros.event.repository;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
            @NotNull EventLevel level,
            String description                                                           ,
            String Log,
            String origin,
            LocalDateTime eventDate,
            Integer quantity);

    Optional<Event> findByLog(String Log);

    Optional<Event> findByLevel(@NotNull EventLevel level);

    @Query(value = "UPDATE events SET quantity = :quantity WHERE log = :log", nativeQuery = true)
    Optional<Event> updateByQuantity(@Param("log") String log,
                                     @Param("quantity") Integer quantity);


}
