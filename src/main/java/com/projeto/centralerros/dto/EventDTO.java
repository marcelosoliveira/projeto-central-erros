package com.projeto.centralerros.dto;

import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.event.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventDTO implements Serializable {

    private Long id;
    @Enumerated(EnumType.STRING)

    private EventLevel level;

    private String description;
    private String origin;
    private String eventDate = generateDate();
    private Integer quantity;


    public EventDTO(Event event) {
        this.id = event.getId();
        this.level = event.getLevel();
        this.description = event.getDescription();
        this.origin = event.getOrigin();
        this.eventDate = generateDate();
        this.quantity = event.getQuantity();
    }

    private String generateDate() {
        Date dataAtual = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(dataAtual);

        return dataFormatada;
    }

}
