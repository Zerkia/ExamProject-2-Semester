package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.User;

import java.util.List;

public interface ProjectRepository {
        Project createProject(Project project) throws ExamProjectException;
        List<Project> fetchProjects(User user);
        List<Project> fetchAllProjects();



}
