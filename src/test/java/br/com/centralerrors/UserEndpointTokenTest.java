package br.com.centralerrors;

import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class UserEndpointTokenTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int PORT;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private HttpEntity<Void> userHeader;
    private HttpEntity<Void> adminHeader;
    private HttpEntity<Void> wrongHeader;

    @TestConfiguration
    static class Config{
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("admin", "admin");
        }
    }

    @Before
    public void configUserHeader() {
        String str = "{ \"username\": \"teste\", \"password\": \"teste\" }";
        HttpHeaders headers = restTemplate.postForEntity(
                "/oauth/token", str, String.class).getHeaders();
        this.userHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configAdminHeader() {
        String str = "{ \"username\": \"bob\", \"password\": \"123456789\" }";
        HttpHeaders headers = restTemplate.postForEntity(
                "/oauth/token", str, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configWrongHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "083762895");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Before
    public void setup() {
        Optional<User> user = Optional.of(new User(
                1L, "Marcelo", "marcelo@hotmail.com", "bob", "123456789"));

        BDDMockito.when(this.userRepository.findById(user.get().getId())).thenReturn(user);
    }

    @Test
    public void listUsersIsTokenIncorrectStatusCode403() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getByIdUserTokenIncorrectStatusCode403() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users/1", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listUsersTokenCorrectStatusCode200() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users/", HttpMethod.GET, adminHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getByUserTokenCorrectStatusCode200() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users/{id}", HttpMethod.POST, adminHeader, String.class, 1L);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getByUserTokenNotFoundStatusCode404() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users/{id}", HttpMethod.GET, adminHeader, String.class, -2);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteByUserAdminTokenStatusCode200() {
        BDDMockito.doNothing().when(this.userRepository).deleteById(1L);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users/{id}", HttpMethod.DELETE, adminHeader, String.class, 1L);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteByUserAdminTokenIsNotExistsStatusCode404() throws Exception {
        String token = adminHeader.getHeaders().get("Authorization").get(0);
        BDDMockito.doNothing().when(this.userRepository).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/admin/users", -1L)
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void CreateUserCorrectStatusCode201() throws Exception {
        User user = new User(
                5L, "Jow Man", "jow@hotmail.com", "jow", "123456789");

        BDDMockito.when(this.userRepository.save(user)).thenReturn(user);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/api/v1/users/", user, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void CreateUserAdminTokenCorrectStatusCode201() throws Exception {
        User user = new User(
                5L, "Jow Man", "jow@hotmail.com", "jow", "123456789");

        BDDMockito.when(this.userRepository.save(user)).thenReturn(user);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users/", HttpMethod.GET, adminHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
}
