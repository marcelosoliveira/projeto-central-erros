package br.com.centralerrors.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class EventLogDTO {

    private UUID id;
    private String log;

}
