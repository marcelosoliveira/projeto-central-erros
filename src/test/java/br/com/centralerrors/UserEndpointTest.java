package br.com.centralerrors;

import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int PORT;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class Config{
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("bob", "123456789");
        }
    }

    @Test
    public void listUsersIncorrectStatusCode401() {
        System.out.println(PORT);
        restTemplate = restTemplate.withBasicAuth("user","user");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/v1/admin/users", String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void getByIdUserIncorrectStatusCode401() {
        System.out.println(PORT);
        restTemplate = restTemplate.withBasicAuth("user","user");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/v1/admin/users/1", String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listUsersCorrectStatusCode200() {
        List<User> users = asList(
                new User(1L, "Marcelo", "marcelo@hotmail.com", "bob", "123456789"),
                new User(2L, "Marcelo Ivan", "saga@hotmail.com", "saga", "123456789"),
                new User(3L, "Ricardo Alves", "onfire@hotmail.com", "onfire", "123456789"),
                new User(4L, "Jow Man", "jow@hotmail.com", "jow", "123456789"));
        BDDMockito.when(this.userRepository.findAll()).thenReturn(users);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/v1/admin/users", String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getByUserCorrectStatusCode200() {
        Optional<User> user = Optional.of(new User(
                1L, "Marcelo", "marcelo@hotmail.com", "bob", "123456789"));

        BDDMockito.when(this.userRepository.findById(user.get().getId())).thenReturn(user);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/api/v1/admin/users/{id}", String.class, user.get().getId());
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getByUserNotFoundStatusCode404() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/api/v1/admin/users/{id}", String.class, -2);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteByUserAdminStatusCode200() {
        Optional<User> user = Optional.of(new User(
                1L, "Marcelo", "marcelo@hotmail.com", "bob", "123456789"));

        BDDMockito.when(this.userRepository.findById(user.get().getId())).thenReturn(user);
        BDDMockito.doNothing().when(this.userRepository).deleteById(1L);
        ResponseEntity<String> exchange = this.restTemplate.exchange(
                "/api/v1/admin/users", HttpMethod.DELETE, null, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @WithMockUser(username = "bob", password = "12345678", roles = {"USER", "ADMIN"})
    public void deleteByUserAdminIsNotExistsStatusCode404() throws Exception {
        Optional<User> user = Optional.of(new User(
                1L, "Marcelo", "marcelo@hotmail.com", "bob", "123456789"));

        BDDMockito.when(this.userRepository.findById(user.get().getId())).thenReturn(user);
        BDDMockito.doNothing().when(this.userRepository).deleteById(1L);
        // ResponseEntity<String> exchange = this.restTemplate.exchange(
        //        "/api/v1/admin/users", HttpMethod.DELETE, null, String.class, -1L);
        // Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/admin/users", -1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void CreateUserCorrectStatusCode201() throws Exception {
        User user = new User(
                5L, "Jow Man", "jow@hotmail.com", "jow", "123456789");

        BDDMockito.when(this.userRepository.save(user)).thenReturn(user);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/api/v1/users/", user, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
