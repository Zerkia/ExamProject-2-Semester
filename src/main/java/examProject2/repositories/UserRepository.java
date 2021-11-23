package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.User;

public interface UserRepository {
    public User createUser(User user) throws ExamProjectException;
}
