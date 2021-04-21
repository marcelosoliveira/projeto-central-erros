package com.projeto.centralerros.user.service.impl;

import com.projeto.centralerros.user.model.User;
import com.projeto.centralerros.user.repository.UserRepository;
import com.projeto.centralerros.user.service.interfaces.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, UserServiceInterface {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = Optional.ofNullable(this.userRepository.findByUserName(userName))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente ou senha invalida"));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList(
                "ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList(
                "ROLE_USER");

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), user.isAdmin() ? authorityListAdmin
                : authorityListUser);
    }

    @Override
    public String passwordCrypto(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
