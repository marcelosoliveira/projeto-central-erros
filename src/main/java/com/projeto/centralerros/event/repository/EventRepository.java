package com.projeto.centralerros.event.repository;

import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.enums.EventLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByLevelOrDescriptionOrLogOrOriginOrEventDateOrQuantity(
            EventLevel level,
            String description,
            String log,
            String origin,
            LocalDateTime eventDate,
            Integer quantity,
            Pageable pageable);

    @Query(value = "SELECT * FROM events e INNER JOIN users_events ue ON e.id = ue.id_event" +
            " INNER JOIN users u ON u.id = ue.id_user WHERE e.id = :id AND u.id = :idUser", nativeQuery = true)
    Optional<Event> findByIdLog(@Param("id") Long id, @Param("idUser") Long idUser);

    @Query(value = "SELECT * FROM events e INNER JOIN users_events ue ON e.id = ue.id_event " +
            "INNER JOIN users u ON ue.id_user = u.id WHERE u.id = :idUser", nativeQuery = true)
    Page<Event> findAll(@Param("idUser") Long idUser, Pageable pageable);

     @Query(value = "SELECT e.id, e.event_date, e.quantity, e.level, " +
             "e.log, e.description, e.origin FROM events e " +
             "INNER JOIN users_events ue ON e.id = ue.id_event " +
             "INNER JOIN users u ON u.id = ue.id_user " +
             "WHERE e.level = :level AND e.log = :log AND e.description = :description " +
             "AND e.origin = :origin AND u.id= :idUser", nativeQuery = true)
     Optional<Event> findEventExist(
            @Param("level") String level,
            @Param("log") String log,
            @Param("description") String description,
            @Param("origin") String origin,
            @Param("idUser") Long idUser);

}
