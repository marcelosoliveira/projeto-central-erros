package br.com.centralerrors.user.model;

import br.com.centralerrors.event.model.Event;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@EntityListeners(EntityListeners.class)
@EqualsAndHashCode(of = "id")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull(message = "O campo name não pode ser nulo!")
    @NotBlank(message = "O campo name não pode estar em branco!")
    @NotEmpty(message = "O campo name não pode ser vazio!")
    @Column
    @Getter
    @Setter
    private String name;

    @NotNull(message = "O campo email não pode ser nulo!")
    @NotBlank(message = "O campo email não pode estar em branco!")
    @NotEmpty(message = "O campo email não pode ser vazio!")
    @Column
    @Getter
    @Setter
    @Email
    private String email;

    @NotNull(message = "O campo userName não pode ser nulo!")
    @NotBlank(message = "O campo userName não pode estar em branco!")
    @NotEmpty(message = "O campo userName não pode ser vazio!")
    @Column(unique = true)
    @Getter
    @Setter
    private String userName;

    @NotNull(message = "O campo password não pode ser nulo!")
    @NotBlank(message = "O campo password não pode estar em branco!")
    @NotEmpty(message = "O campo password não pode ser vazio!")
    @JsonIgnore
    @Column
    @Getter
    @Setter
    private String password;

    @NotNull
    @Column
    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    private Boolean isAdmin = false;

    @ManyToMany
    @JoinTable(name = "Users_Events",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEvent"))
    private Set<Event> events;

}
