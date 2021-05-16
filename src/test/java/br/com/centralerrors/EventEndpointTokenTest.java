package br.com.centralerrors;

import br.com.centralerrors.enums.EventLevel;
import br.com.centralerrors.event.model.Event;
import br.com.centralerrors.event.repository.EventRepository;
import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventEndpointTokenTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int PORT;

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

    private HttpEntity<Void> userHeader;
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
    public void configWrongHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "083762895");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Before
    public void setup() {
        Event event = new Event(UUID.randomUUID(),
                EventLevel.ERROR, "Java Error test units",
                "Java Exception", "Spring Boot");

        BDDMockito.when(this.eventRepository.findById(event.getId())).thenReturn(event);
    }

    @Test
    public void listEventsIsTokenIncorrectStatusCode403() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/events/all", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getByIdEventTokenIncorrectStatusCode403() {
        Optional<Event> event = Optional.of(new Event(UUID.randomUUID(),
                EventLevel.ERROR, "Java Error test units",
                "Java Exception", "Spring Boot"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/events/{id}", HttpMethod.GET, wrongHeader, String.class, event.get().getId());
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listEventsTokenCorrectStatusCode200() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/events/all", HttpMethod.GET, userHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getByEventTokenCorrectStatusCode200() {
        Optional<Event> event = Optional.of(new Event(UUID.randomUUID(),
                EventLevel.ERROR, "Java Error test units",
                "Java Exception", "Spring Boot"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/events/{id}", HttpMethod.GET, userHeader, String.class, event.get().getId());
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getByEventTokenNotFoundStatusCode404() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/events/{id}", HttpMethod.GET, userHeader,
                String.class, "83r839rh83hr839rh83hr8h");
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void CreateEventTokenCorrectStatusCode201() throws Exception {
        Event event = new Event(UUID.randomUUID(),
                EventLevel.ERROR, "Java Error test units",
                "Java Exception", "Spring Boot");

        BDDMockito.when(this.eventRepository.save(event)).thenReturn(event);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/admin/users", HttpMethod.GET, userHeader, String.class);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
}
