package com.projeto.centralerros.event.model;

import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.user.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@EntityListeners(EntityListeners.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private EventLevel level;

    @NotNull
    @NotBlank
    @Column
    @Getter
    @Setter
    private String description;

    @NotNull
    @NotBlank
    @Column
    @Getter
    @Setter
    private String log;

    @NotNull
    @NotBlank
    @Column
    @Getter
    @Setter
    private String origin;

    @NotNull
    @Column
    @Getter
    @Setter
    private String eventDate = generateDate();

    @NotNull
    @Column
    @Positive
    @Getter
    @Setter
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "Users_Events",
            joinColumns = @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> users;

    public String generateDate() {
        Date dataAtual = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(dataAtual);

        return dataFormatada;
    }
}
