package br.com.centralerrors.dto;

import br.com.centralerrors.enums.EventLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class EventDTO {

    private UUID id;
    @Enumerated(EnumType.STRING)
    private EventLevel level;
    private String description;
    private String origin;
    private LocalDate eventDate;
    private Integer quantity;

}
