package br.com.centralerrors.event.model;

import br.com.centralerrors.enums.EventLevel;
import br.com.centralerrors.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@EntityListeners(EntityListeners.class)
@EqualsAndHashCode(of = "id")
@Table(name = "events")
public class Event {

    public Event(UUID id, EventLevel level, String description, String log, String origin) {
        this.id = id;
        this.level = level;
        this.description = description;
        this.log = log;
        this.origin = origin;
    }

    public Event() {
    }

    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @Getter
    @ApiModelProperty(hidden = true)
    private UUID id;

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
    @ApiModelProperty(hidden = true)
    private LocalDate eventDate = LocalDate.now();

    @NotNull
    @Column
    @Positive(message = "O campo quantity não pode ter o valor negativo!")
    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    private Integer quantity = 1;

    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JsonIgnore
    private User user;

}
