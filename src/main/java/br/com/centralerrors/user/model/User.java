package br.com.centralerrors.user.model;

import br.com.centralerrors.event.model.Event;
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
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column
    @Getter
    @Setter
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column
    @Getter
    @Setter
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(unique = true)
    @Getter
    @Setter
    private String userName;

    @NotNull
    @NotBlank
    @NotEmpty
    @JsonIgnore
    @Column
    @Getter
    @Setter
    private String password;

    @NotNull
    @Column
    @Getter
    @Setter
    @JsonIgnore
    private boolean isAdmin = false;

    @ManyToMany
    @JoinTable(name = "Users_Events",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEvent"))
    private Set<Event> events;

}
