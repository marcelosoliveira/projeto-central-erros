package com.projeto.centralerros.dto;

import com.projeto.centralerros.enums.EventLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventDTO {

    private Long id;
    @Enumerated(EnumType.STRING)
    private EventLevel level;
    private String log;
    private String description;
    private String origin;
    private String eventDate = generateDate();
    private Integer quantity;

    private String generateDate() {
        Date dataAtual = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(dataAtual);

        return dataFormatada;
    }

}
