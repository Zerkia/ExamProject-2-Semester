package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepository {
        List<Project> fetchProjects(User user);
        List<Project> fetchAllProjects();
        Project createProject(Project project) throws ExamProjectException;
        String updateProject(int projectID, String projectName, LocalDateTime deadline);
        String deleteProject(int projectID);

        //space between main and sub

        List<SubProject> fetchSubProjects(int projectID);
        SubProject createSubproject(SubProject subProject) throws ExamProjectException;
        String updateSubproject(int subprojectID, String subprojectName, LocalDateTime deadline);
        String deleteSubproject(int subprojectID);
}
