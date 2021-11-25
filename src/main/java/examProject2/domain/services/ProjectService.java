package examProject2.domain.services;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.repositories.ProjectRepository;

public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){this.projectRepository = projectRepository;}

    public Project createProject(String projectName, int userID) throws ExamProjectException {
        Project project = new Project(projectName, userID);
        return projectRepository.createProject(project);

    }
}
