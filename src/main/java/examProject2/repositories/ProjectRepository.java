package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepository {
        Project createProject(Project project) throws ExamProjectException;
        List<Project> fetchProjects(User user);
        List<Project> fetchAllProjects();
        String deleteProject(int projectID);
        String editProject(int projectID, String projectName, LocalDateTime deadline) throws SQLException;
        List<SubProject> fetchSubProjects(int projectID);
        SubProject createSubproject(SubProject subProject) throws ExamProjectException;
}
