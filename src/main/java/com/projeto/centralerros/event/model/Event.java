package com.projeto.centralerros.event.model;

import com.projeto.centralerros.enums.EventLevel;
import com.projeto.centralerros.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "events")
@EqualsAndHashCode(of = "id")
@EntityListeners(EntityListeners.class)
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull(message = "O campo level não pode ser nulo!")
    @Column
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private EventLevel level;

    @NotNull(message = "O campo description não pode ser nulo!")
    @NotBlank(message = "O campo description não pode estar em branco!")
    @NotEmpty(message = "Campo description não pode ser vazio")
    @Column
    @Getter
    @Setter
    private String description;

    @NotNull(message = "O campo log não pode ser nulo!")
    @NotBlank(message = "O campo log não pode estar em branco!")
    @NotEmpty(message = "Campo log não pode ser vazio")
    @Column
    @Getter
    @Setter
    private String log;

    @NotNull(message = "O campo origin não pode ser nulo!")
    @NotEmpty(message = "Campo origin não pode ser vazio")
    @NotBlank(message = "O campo origin não pode estar em branco!")
    @Column
    @Getter
    @Setter
    private String origin;

    @NotNull
    @Column
    @Getter
    @Setter
    private LocalDateTime eventDate = LocalDateTime.now();

    @NotNull
    @Column
    @Positive
    @Getter
    @Setter
    private Integer quantity = 1;

    @ManyToMany
    @JoinTable(name = "Users_Events",
            joinColumns = @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idUser"))
    private Set<User> users = new HashSet<>();

    public LocalDateTime generateDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(LocalDateTime.now());

        return LocalDateTime.parse(dataFormatada);
    }
}
