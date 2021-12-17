package examProject2;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.User;
import examProject2.repositories.UserRepositoryImplemented;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTests {
    @Test
    void contextLoads() {
    }
    /**
     *
     * @author Mads Haderup
     */
    @Test
    void LoginTest() throws ExamProjectException {
        UserRepositoryImplemented repo = new UserRepositoryImplemented();
        User user = repo.login("SuperAdmin","superadmin");
        User user1 = new User("SuperAdmin","superadmin");

        String str1 = user1.toString();
        String str2 = user.toString();
        assertThat(str1).isEqualTo(str2);
    }
    /**
     *
     * @author Mads Haderup
     */
    @Test
    void createUserTest() throws ExamProjectException {
        UserRepositoryImplemented repo = new UserRepositoryImplemented();
        Random random = new Random();
        String a ="ABCDEFGHJIKLMNOPQRSTUVWXYZ";
        int i = random.nextInt(23);
        int j = random.nextInt(23);
        int k = random.nextInt(23);

        String username = a.substring(i,i+1) + a.substring(j,j+1) + a.substring(k,k+1);
        String password = a.substring(k,k+1) + a.substring(j,j+1) + a.substring(i,i+1);

        User user1 = new User(username, password);
        User user2 = repo.createUser(user1);
        User user3 = repo.login(user2.getUsername(),user2.getPassword());
        String str1 = user1.toString();
        String str2 = user3.toString();

        assertThat(str1).isEqualTo(str2);
    }
}
