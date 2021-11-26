package examProject2.domain.services;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.User;
import examProject2.repositories.ProjectRepository;

import java.util.List;

public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){this.projectRepository = projectRepository;}

    public Project createProject(String projectName, int userID) throws ExamProjectException {
        Project project = new Project(projectName, userID);
        return projectRepository.createProject(project);

    }

    public List<Project> fetchProjects(User user){
        return projectRepository.fetchProjects(user);
    }

    public List<Project> fetchAllProjects(){
        return projectRepository.fetchAllProjects();
    }

}
