package com.projeto.centralerros.central.repository;

import com.projeto.centralerros.central.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByLevelAndDescriptionAndLogAndOrigin(EventLevel level,
                                                             String description                                                           ,
                                                             String Log,
                                                             String origin);

    Optional<Event> findByLog(String Log);

    Optional<Event> findByLevel(@NotNull EventLevel level);

    @Query(value = "UPDATE events SET quantity = :quantity WHERE log = :log", nativeQuery = true)
    Optional<Event> updateByQuantity(@Param("log") String log,
                                     @Param("quantity") Integer quantity);
    //@NotNull @Positive Integer findByQuantity();
}
