package br.com.centralerrors.user.controller;

import br.com.centralerrors.exceptions.ResponseBadRequestException;
import br.com.centralerrors.dto.UserDTO;
import br.com.centralerrors.exceptions.ResponseNotFoundException;
import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import br.com.centralerrors.user.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("v1")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    private ModelMapper modelMapper;

    @GetMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> userList(Pageable pageable) {
        Page<UserDTO> userDto = this.userRepository.findAll(pageable).map(this::toUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<UserDTO>> userId(@PathVariable("id") Long id) {
        verifyUserId(id);
        Optional<UserDTO> user = this.userRepository.findById(id).map(this::toUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Optional<UserDTO>> createUser(@Valid @RequestBody User user) {
        verifyUserName(user);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<UserDTO> userDto = Optional.ofNullable(
                this.userRepository.save(user)).map(this::toUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/users")
    public ResponseEntity<Optional<UserDTO>> userUpdate(@Valid @RequestBody User user) {
        verifyUserId(user.getId());
        user.setAdmin(false);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(this.userRepository.save(user));
        return ResponseEntity.status(HttpStatus.OK).body(userDto.map(this::toUserDTO));
    }

    @PostMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<UserDTO>> createUserAdmin(@Valid @RequestBody User user) {
        verifyUserName(user);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<UserDTO> userDto = Optional.ofNullable(
                this.userRepository.save(user)).map(this::toUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<UserDTO>> userUpdateAdmin(@Valid @RequestBody User user) {
        verifyUserId(user.getId());
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(this.userRepository.save(user));
        return ResponseEntity.status(HttpStatus.OK).body(userDto.map(this::toUserDTO));
    }

    @DeleteMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> userDelete(@PathVariable("id") Long id) {
        verifyUserId(id);
        this.userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\": \"Usuário deletado com sucesso.\"}");
    }

    private UserDTO toUserDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    private void verifyUserId(Long id) {
        if (!this.userRepository.findById(id).isPresent()) {
            throw new ResponseNotFoundException("Usuário não cadastrado id: " + id);
        }
    }

    private void verifyUserName(User user) {
        Optional<User> userName = Optional.ofNullable(
                this.userRepository.findByUserName(user.getUserName()));
        if (userName.isPresent()) {
            throw  new ResponseBadRequestException("Username " + user.getUserName() + " já existe!");
        }
    }

}
