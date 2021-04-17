package com.projeto.centralerros.dto;

import com.projeto.centralerros.enums.EventLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
public class LogDTO {

    private Long id;
    private String log;

}
