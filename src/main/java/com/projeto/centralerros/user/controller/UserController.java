package com.projeto.centralerros.user.controller;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.dto.UserDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.exceptions.ResponseNotFoundException;
import com.projeto.centralerros.user.model.User;
import com.projeto.centralerros.user.repository.UserRepository;
import com.projeto.centralerros.user.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    private ModelMapper modelMapper;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> userList() {
        List<UserDTO> userDto = this.userRepository.findAll().stream().map(this::toUserDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> userList(@PathVariable("id") Long id) {
        verifyUserId(id);
        Optional<UserDTO> user = this.userRepository.findById(id).map(this::toUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    public Optional<UserDTO> create(@Valid @RequestBody User user) {
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(this.userRepository.save(user));
        return userDto.map(this::toUserDTO);
    }

    private UserDTO toUserDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    private void verifyUserId(Long id) {
        if (this.userRepository.findById(id).isEmpty()) {
            throw new ResponseNotFoundException("Usuário não cadastrado id: " + id);
        }
    }

}
