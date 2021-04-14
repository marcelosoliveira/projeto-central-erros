package com.projeto.centralerros.event.model;

import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(EntityListeners.class)
@Table(name = "events")
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
    @Column
    @Getter
    @Setter
    private String description;

    @NotNull
    @Column
    @Getter
    @Setter
    private String log;

    @NotNull
    @Column
    @Getter
    @Setter
    private String origin;

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

    private String generateDate() {
        Date dataAtual = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(dataAtual);

        return dataFormatada;
    }
}
