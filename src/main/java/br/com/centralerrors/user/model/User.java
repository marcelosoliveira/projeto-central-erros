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

@Data
@Entity
@EntityListeners(EntityListeners.class)
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {

    public User(Long id, String name, String email, String userName, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User(String name, String email, String userName, String password) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

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
    @Email(message = "Formato do email inválido!")
    private String email;

    @NotNull(message = "O campo userName não pode ser nulo!")
    @NotBlank(message = "O campo userName não pode estar em branco!")
    @NotEmpty(message = "O campo userName não pode ser vazio!")
    @Column
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Event> events;

}
