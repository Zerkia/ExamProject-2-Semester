package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.User;

import java.util.List;

public interface UserRepository {
    public User createUser(User user) throws ExamProjectException;
    public User login(String username, String password) throws ExamProjectException;
    public List<Project> fetchProjects(User user);
    public List<Project> fetchAllProjects();
}
