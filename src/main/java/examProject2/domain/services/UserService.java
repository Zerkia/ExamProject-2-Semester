package examProject2.domain.services;

import examProject2.domain.models.User;
import examProject2.domain.ExamProjectException;

import java.util.List;

import examProject2.repositories.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password, int userroleID) throws ExamProjectException {
        User user = new User(username, password, userroleID);
        return userRepository.createUser(user);
    }

    public User login(String username, String password) throws ExamProjectException {
        return userRepository.login(username, password);
    }
}
