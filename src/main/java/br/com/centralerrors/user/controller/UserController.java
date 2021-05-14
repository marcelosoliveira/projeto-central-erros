package br.com.centralerrors.user.controller;

import br.com.centralerrors.exceptions.ResponseBadRequestException;
import br.com.centralerrors.dto.UserDTO;
import br.com.centralerrors.exceptions.ResponseNotFoundException;
import br.com.centralerrors.secutiry.LoginSecurityUser;
import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import br.com.centralerrors.user.service.impl.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    private ModelMapper modelMapper;

    private LoginSecurityUser loginSecurityUser;

    @GetMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Lista todos usuários, se usuário for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina a ser carregada"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Quantidade de registros"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Ordenacao dos registros"),
    })
    public ResponseEntity<Page<UserDTO>> userList(@PageableDefault(
            sort = "userName", direction = Sort.Direction.ASC, page = 0, size = 100)
                                                      @ApiIgnore Pageable pageable) {
        Page<UserDTO> userDto = this.userRepository.findAll(pageable).map(this::toUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Consulta usuário pelo id, se usuário for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Evento não encontrado")
    })
    public ResponseEntity<Optional<UserDTO>> userId(@PathVariable("id") Long id) {
        verifyUserId(id);
        Optional<UserDTO> user = this.userRepository.findById(id).map(this::toUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping(path = "/users")
    @ApiOperation(value = "Cadastra usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cadastro ok")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "Authorization", defaultValue = "Acesso liberado"),
    })
    public ResponseEntity<Optional<UserDTO>> createUser(@Valid @RequestBody User user) {
        verifyUserName(user);
        user.setIsAdmin(false);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(
                this.userRepository.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto.map(this::toUserDTO));
    }

    @PutMapping("/users")
    @ApiOperation(value = "Atualiza usuário logado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    public ResponseEntity<Optional<UserDTO>> userUpdate(@Valid @RequestBody User user) {
        Long idUser = this.loginSecurityUser.getLoginUser().getId();
        verifyUserId(idUser);
        Boolean roleUser = this.loginSecurityUser.getLoginUser().getIsAdmin();
        if (roleUser) {
            user.setIsAdmin(true);
        } else {
            user.setIsAdmin(false);
        }
        user.setId(idUser);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(this.userRepository.save(user));
        return ResponseEntity.status(HttpStatus.OK).body(userDto.map(this::toUserDTO));
    }

    @PostMapping("admin/users/{isAdmin}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Cadastra usuário ADMIN, se usuário for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cadastro ok")
    })
    public ResponseEntity<Optional<UserDTO>> createUserAdmin(@Valid @RequestBody User user,
                                                             @PathVariable Boolean isAdmin) {
        verifyUserName(user);
        user.setIsAdmin(isAdmin);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(
                this.userRepository.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto.map(this::toUserDTO));
    }

    @PutMapping("admin/users/{id}/{isAdmin}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Atualiza qualquer usuário USER, se usuário for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    public ResponseEntity<Optional<UserDTO>> userUpdateAdmin(@Valid @RequestBody User user,
                                                             @PathVariable("id") Long id,
                                                             @PathVariable Boolean isAdmin) {
        verifyUserId(id);
        Long idUser = this.loginSecurityUser.getLoginUser().getId();
        Optional<User> userAdmin = this.userRepository.findById(id);
        if (userAdmin.get().getIsAdmin() && idUser != id)
            throw new ResponseBadRequestException("Atualização negada! Usuário ADMIN");
        user.setId(id);
        user.setIsAdmin(isAdmin);
        user.setPassword(this.userService.passwordCrypto(user.getPassword()));
        Optional<User> userDto = Optional.ofNullable(this.userRepository.save(user));
        return ResponseEntity.status(HttpStatus.OK).body(userDto.map(this::toUserDTO));
    }

    @DeleteMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Deleta qualquer usuário USER, se usuário for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Excluído com sucesso")
    })
    public ResponseEntity<?> userDelete(@PathVariable("id") Long id) {
        verifyUserId(id);
        Long idUser = this.loginSecurityUser.getLoginUser().getId();
        Optional<User> userAdmin = this.userRepository.findById(id);
        if (userAdmin.get().getIsAdmin() && idUser != id)
            throw new ResponseBadRequestException("Exclusão negada! Usuário ADMIN");
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
