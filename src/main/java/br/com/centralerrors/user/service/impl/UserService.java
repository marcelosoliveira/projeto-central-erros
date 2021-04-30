package br.com.centralerrors.user.service.impl;

import br.com.centralerrors.secutiry.LoginSecurityUser;
import br.com.centralerrors.user.repository.UserRepository;
import br.com.centralerrors.user.service.interfaces.UserServiceInterface;
import br.com.centralerrors.user.model.User;
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

    private LoginSecurityUser loginSecurityUser;

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
