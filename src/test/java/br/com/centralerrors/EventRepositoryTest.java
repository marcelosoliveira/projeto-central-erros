package br.com.centralerrors;

import br.com.centralerrors.enums.EventLevel;
import br.com.centralerrors.event.model.Event;
import br.com.centralerrors.event.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    public ExpectedException thrown = ExpectedException.none();

    Event event = new Event(UUID.randomUUID(),
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
        Event saveEvent = this.eventRepository.save(event);
        Event idEvent = this.eventRepository.findById(saveEvent.getId());
        Assertions.assertThat(idEvent.getId()).isNotNull();
        Assertions.assertThat(idEvent.getLevel()).isEqualTo(EventLevel.ERROR);
        Assertions.assertThat(idEvent.getDescription()).isEqualTo("Java Error test units");
        Assertions.assertThat(idEvent.getLog()).isEqualTo("Java Exception");
        Assertions.assertThat(idEvent.getOrigin()).isEqualTo("Spring Boot");
    }

    @Test
    public void insertEventQuantityNegativeException() {
        this.thrown.expect(ConstraintViolationException.class);
        this.thrown.expectMessage("O campo quantity não pode ter o valor negativo!");
        event.setQuantity(-1);
        this.eventRepository.save(event);
    }
}
