package examProject2;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.User;
import examProject2.repositories.UserRepositoryImplemented;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExamProject2ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void LoginTest() throws ExamProjectException {
        UserRepositoryImplemented repo = new UserRepositoryImplemented();
        User user = repo.login("admin","admin");
        User user1 = new User("admin","admin");
        //user1.setID(1);
        String str1 = user1.toString();
        String str2 = user.toString();
        assertThat(str1).isEqualTo(str2);
    }

}
