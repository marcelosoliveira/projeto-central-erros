package com.projeto.centralerros.user.model;

import com.projeto.centralerros.event.model.Event;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@EntityListeners(EntityListeners.class)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @Column
    @Getter
    @Setter
    private String name;

    @NotNull
    @Column
    @Getter
    @Setter
    @Email
    private String email;

    @NotNull
    @Column
    @Getter
    @Setter
    private String userName;

    @NotNull
    @Column
    @Getter
    @Setter
    private String password;

    @ManyToMany
    @JoinTable(name = "Users_Events",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEvent"))
    private List<Event> events;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(("ADMIN")));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
