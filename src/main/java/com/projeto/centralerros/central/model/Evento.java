package com.projeto.centralerros.central.model;

import com.projeto.centralerros.enuns.NivelEvento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static java.time.LocalDate.now;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private NivelEvento level;

    @NotNull
    @Column
    private String descricao;

    @NotNull
    @Column
    private String log;

    @NotNull
    @Column
    private String origem;

    @NotNull
    @Column
    private LocalDate dataEvento = now();

    @NotNull
    @Column
    @Positive
    private Integer quantidade;
}
