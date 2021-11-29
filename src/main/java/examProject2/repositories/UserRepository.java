package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user) throws ExamProjectException;
    User login(String username, String password) throws ExamProjectException;


}
