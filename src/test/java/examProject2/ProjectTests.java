package examProject2;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.Task;
import examProject2.domain.models.User;
import examProject2.domain.services.ProjectService;
import examProject2.domain.services.UserService;
import examProject2.repositories.ProjectRepositoryImplemented;
import examProject2.repositories.UserRepositoryImplemented;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectTests {
    @Test
    void contextLoads() {
    }

    @Test
    void fetchProjectTest() throws ExamProjectException {
        ProjectService projectService = new ProjectService(new ProjectRepositoryImplemented());
        UserService userService = new UserService(new UserRepositoryImplemented());
        User user = userService.login("SuperAdmin", "superadmin");
        List<Project> projects = projectService.fetchProjects(user);
        assertThat(projects.size()).isEqualTo(1);
    }
    @Test
    void fetchSubProjectTest() {
        ProjectService projectService = new ProjectService(new ProjectRepositoryImplemented());
        List<SubProject> subProjects = projectService.fetchSubprojects(1);
        assertThat(subProjects.size()).isEqualTo(2);
    }

    @Test
    void updateSubProjectTest() {

        ProjectService projectService = new ProjectService(new ProjectRepositoryImplemented());
        List<SubProject> subProjects = projectService.fetchSubprojects(1);
        projectService.updateSubproject(subProjects.get(0),"navneterskiftetnu", "2027-12-11T20:20");
        List<SubProject> subProjects1 = projectService.fetchSubprojects(1);
        assertThat(subProjects.get(0).getSubprojectName()).isNotEqualTo(subProjects1.get(0).getSubprojectName());
        assertThat(subProjects.get(0).getDeadline()).isNotEqualTo(subProjects1.get(0).getDeadline());
    }

    @Test
    void updateProjectTest() throws ExamProjectException {

        ProjectService projectService = new ProjectService(new ProjectRepositoryImplemented());
        UserService userService = new UserService(new UserRepositoryImplemented());
        User user = userService.login("SuperAdmin", "superadmin");
        List<Project> projects = projectService.fetchProjects(user);
        Project project = projects.get(0);
        projectService.updateProject(project,"navneterskiftetnu","2024-12-12T23:59");
        List<Project> projects1 = projectService.fetchProjects(user);
        assertThat(projects.get(0).getProjectName()).isNotEqualTo(projects1.get(0).getProjectName());
        assertThat(projects.get(0).getDeadline()).isNotEqualTo(projects1.get(0).getDeadline());
    }

    @Test
    void addAndDeleteProjectsTest() throws ExamProjectException{
        ProjectRepositoryImplemented repo = new ProjectRepositoryImplemented();

        ProjectService projectService = new ProjectService(repo);

        List<Project> projects = projectService.fetchAllProjects();
        projectService.createProject("UnitTest4", 4, "2022-03-22T22:22");


        List<Project> projects1 = projectService.fetchAllProjects();
        int i = projects.size();
        assertThat(i).isLessThan(projects1.size());

        projectService.deleteProject(projects1.get(i-1).getProjectID());
        List<Project> projects2 = projectService.fetchAllProjects();

        assertThat(projects2.size()).isEqualTo(projects.size());

    }

    @Test
    void addAndDeleteSubProjectsTest() throws ExamProjectException{
        ProjectRepositoryImplemented repo = new ProjectRepositoryImplemented();
        ProjectService projectService = new ProjectService(repo);

        List<SubProject> subProjects = projectService.fetchSubprojects(3);
        projectService.createSubproject("UnitTest42virkknu", 3, 4, "2022-03-22T22:22");
        int i = subProjects.size();

        List<SubProject> subProjects1 = projectService.fetchSubprojects(3);

        assertThat(subProjects1.size()).isGreaterThan(i);

        projectService.deleteSubproject(subProjects1.get(subProjects1.size()-1).getSubprojectID());
        List<SubProject> subProjects2 = projectService.fetchSubprojects(3);

        assertThat(subProjects2.size()).isEqualTo(subProjects.size());

    }


}
