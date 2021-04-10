package com.projeto.centralerros.central.service.interfaces;

import com.projeto.centralerros.central.model.Event;
import com.projeto.centralerros.enums.EventLevel;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface EventServiceInterface {

    Optional<Event> levelCondition(@NotNull EventLevel level, String description, String log, String origin);

}
