package examProject2.domain.services;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;
import examProject2.repositories.ProjectRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectService {
    private ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository){this.projectRepository = projectRepository;}

    public Project createProject(String projectName, int userID, LocalDateTime deadline) throws ExamProjectException {
        Project project = new Project(projectName,userID,deadline);
        return projectRepository.createProject(project);
    }
    public List<Project> fetchProjects(User user){
        return projectRepository.fetchProjects(user);
    }
    public List<Project> fetchAllProjects(){
        return projectRepository.fetchAllProjects();
    }
    public String updateProject(int projectID, String projectName, LocalDateTime deadline) {
        return projectRepository.updateProject(projectID, projectName, deadline);
    }
    public String deleteProject(int projectID){ return projectRepository.deleteProject(projectID); }

    //space between main and sub

    public SubProject createSubproject(String projectName, int userID, int projectID, LocalDateTime deadline) throws ExamProjectException {
        SubProject subProject = new SubProject(projectName, userID, projectID, deadline);
        return projectRepository.createSubproject(subProject);
    }
    public List<SubProject> fetchSubprojects(int projectID){return projectRepository.fetchSubProjects(projectID);}
    public String updateSubproject(int subprojectID, String subprojectName, LocalDateTime deadline) {
        return projectRepository.updateSubproject(subprojectID, subprojectName, deadline);
    }
    public String deleteSubproject(int subprojectID){ return projectRepository.deleteSubproject(subprojectID); }

}
