package br.com.centralerrors.secutiry;

import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginSecurityUser {

    private UserRepository userRepository;

    public User getLoginUser() {
        Object loginUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (loginUser instanceof UserDetails) {
            username = ((UserDetails)loginUser).getUsername();
        } else {
            username = loginUser.toString();
        }

        return userRepository.findByUserName(username);
    }

}
