package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;

public interface ProjectRepository {
        public Project createProject(Project project) throws ExamProjectException;


}
