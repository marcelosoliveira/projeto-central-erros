package com.projeto.centralerros.user.controller;

import com.projeto.centralerros.dto.EventDTO;
import com.projeto.centralerros.dto.UserDTO;
import com.projeto.centralerros.event.model.Event;
import com.projeto.centralerros.exceptions.ResponseException;
import com.projeto.centralerros.user.model.User;
import com.projeto.centralerros.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @GetMapping("/users")
    public List<UserDTO> userList() {
        return this.userRepository.findAll().stream().map(this::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public Object userList(@PathVariable("id") Long id) throws ResponseException {
        try {
            Optional<UserDTO> user = this.userRepository.findById(id).map(this::toUserDTO);
            if (user.isEmpty()) {
                throw new ResponseException("Usuário não cadastrado!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (ResponseException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\":\"" + r.getMessage() + "\" }");
        }
    }

    @PostMapping("/users")
    public Optional<UserDTO> create(@RequestBody User user) {
        Optional<User> userDto = Optional.ofNullable(this.userRepository.save(user));
        return userDto.map(this::toUserDTO);
    }

    private UserDTO toUserDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    private EventDTO toEventDTO(Event event) {
        return this.modelMapper.map(event, EventDTO.class);
    }

}
