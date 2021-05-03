package br.com.centralerrors;

import br.com.centralerrors.enums.EventLevel;
import br.com.centralerrors.event.model.Event;
import br.com.centralerrors.event.repository.EventRepository;
import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Event event = new Event(UUID.fromString("3ee0af9c-411f-46b1-9b23-16d8ca96344d"),
            EventLevel.ERROR, "Java Error test units",
            "Java Exception", "Spring Boot");

    @Test
    public void createEventData() {
        this.eventRepository.save(event);
        Assertions.assertThat(event.getId()).isNotNull();
        Assertions.assertThat(event.getLevel()).isEqualTo(EventLevel.ERROR);
        Assertions.assertThat(event.getDescription()).isEqualTo("Java Error test units");
        Assertions.assertThat(event.getLog()).isEqualTo("Java Exception");
        Assertions.assertThat(event.getOrigin()).isEqualTo("Spring Boot");

    }

    @Test
    public void getEventIdData() {
        Event idEvent = this.eventRepository.findById(event.getId());
        System.out.println(event.getId());
        Assertions.assertThat(idEvent.getId()).isNull();
        Assertions.assertThat(idEvent.getLevel()).isEqualTo(EventLevel.ERROR);
        Assertions.assertThat(idEvent.getDescription()).isEqualTo("Java Error test units");
        Assertions.assertThat(idEvent.getLog()).isEqualTo("Java Exception");
        Assertions.assertThat(idEvent.getOrigin()).isEqualTo("Spring Boot");
    }

    @Test
    public void insertEventNull() {
        this.thrown.expect(ConstraintViolationException.class);
        this.thrown.expectMessage("O campo level não pode ser nulo!");
        event.setLevel(null);
        this.eventRepository.save(event);
    }
}
