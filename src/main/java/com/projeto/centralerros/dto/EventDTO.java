package com.projeto.centralerros.dto;

import com.projeto.centralerros.enums.EventLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class EventDTO {

    private Long id;
    @Enumerated(EnumType.STRING)
    private EventLevel level;
    private String description;
    private String origin;
    private String eventDate;
    private Integer quantity;

}
