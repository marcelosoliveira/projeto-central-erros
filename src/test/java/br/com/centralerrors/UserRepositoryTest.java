package br.com.centralerrors;

import br.com.centralerrors.user.model.User;
import br.com.centralerrors.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    User user = new User("Jow Man", "jow@hotmail.com", "jow", "123456789");

    @Test
    public void createUserData() {
        this.userRepository.save(user);
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getUserName()).isEqualTo("jow");
        Assertions.assertThat(user.getPassword()).isEqualTo("123456789");
        Assertions.assertThat(user.getIsAdmin()).isEqualTo(false);
    }

    @Test
    public void deleteUserData() {
        this.userRepository.save(user);
        this.userRepository.deleteById(user.getId());
        Assertions.assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    public void updateUserData() {
        this.userRepository.save(user);
        Assertions.assertThat(user.getIsAdmin()).isEqualTo(false);
        user.setIsAdmin(true);
        this.userRepository.save(user);
        Assertions.assertThat(user.getIsAdmin()).isEqualTo(true);
    }

    @Test
    public void findByUserNameData() {
        this.userRepository.save(user);
        User userName = this.userRepository.findByUserName(user.getUserName());
        Assertions.assertThat(userName.getUserName()).isEqualTo("jow");
    }

    @Test
    public void insertEmailNotFormat() {
        this.thrown.expect(ConstraintViolationException.class);
        this.thrown.expectMessage("Formato do email inválido!");
        user.setEmail("jowjow.com.br");
        this.userRepository.save(user);
    }
}
