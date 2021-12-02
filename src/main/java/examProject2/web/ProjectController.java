package examProject2.web;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;
import examProject2.domain.services.ProjectService;
import examProject2.repositories.ProjectRepositoryImplemented;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
public class ProjectController {
    private ProjectService projectService = new ProjectService(new ProjectRepositoryImplemented());

    @GetMapping("/mainPage")
    public String mainPage(Model model, WebRequest request){
        User user = (User) request.getAttribute("user", 1);

        assert user != null;
        if(user.getUserroleID() <= 2){
            model.addAttribute("projects", projectService.fetchAllProjects());
            request.setAttribute("projects", projectService.fetchAllProjects(), 1);
        } else {
            model.addAttribute("projects", projectService.fetchProjects(user));
            request.setAttribute("projects", projectService.fetchProjects(user), 1);
        }

        return "mainPage";
    }

    @GetMapping("/newProject")
    public String newProject(){return "newProject";}

    @PostMapping("/createProject")
    public RedirectView createProject(WebRequest request) throws ExamProjectException {
        String projectname = request.getParameter("projectName");
        String deadlineDate = request.getParameter("deadline");

        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dt, formatter);


        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        assert user != null;
        Project project = projectService.createProject(projectname, user.getUserID(),localDateTime);
        request.setAttribute("project", project, WebRequest.SCOPE_SESSION);
        return new RedirectView("mainPage");
    }

    @GetMapping("/updateProjectRedirect")
    public RedirectView updateProjectRedirect(int projectID, WebRequest request) {
        List<Project> lst = (List<Project>) request.getAttribute("projects",1);
        for(Project pro : lst){
            if(pro.getProjectID() == projectID){
                request.setAttribute("projectInEditing", pro,1);
                Project test = (Project) request.getAttribute("projectInEditing",1);
                System.out.println(test.getProjectID());
            }
        }
        //needs to use model and potentially webrequest due to ID being in URL
        return new RedirectView("updateProject");
    }

    @GetMapping("/updateProject")
    public String updateProject(WebRequest request){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;
        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        Project project = (Project) request.getAttribute("projectInEditing",1);
        assert project != null;
        String ownerID = project.getProjectOwner();
        if(username.equals(ownerID)) {
            return "updateProject";
        } else if(userRoleID <= 2) {
            return "updateProject";
        } else {
            return "/error";
        }
    }

    @PostMapping("/editProject")
    public RedirectView editProject(WebRequest request) throws SQLException {
        String projectName = request.getParameter("projectName");
        String deadlineDate = request.getParameter("deadline");
        Project project = (Project) request.getAttribute("projectInEditing", 1);
        assert project != null;
        int projectID = project.getProjectID();

        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dt, formatter);

        String res = projectService.editProject(projectID, projectName, deadline);
        return new RedirectView(res);

    }

    @GetMapping("/deleteProject")
    public RedirectView deleteProject(int projectID) {
        projectService.deleteProject(projectID);
        return new RedirectView("mainPage");
    }


    @GetMapping("/subprojectsPage")
    public String subprojectsPage(Model model, WebRequest request){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;
        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        Project owner = (Project) request.getAttribute("parentProject",1);
        assert owner != null;
        String ownerID = owner.getProjectOwner();
        int projectID = owner.getProjectID();
        if(username.equals(ownerID)) {
            model.addAttribute("subprojects", projectService.fetchSubprojects(projectID));
            request.setAttribute("subprojects", projectService.fetchSubprojects(projectID),1);
            return "subprojectsPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("subprojects", projectService.fetchSubprojects(projectID));
            request.setAttribute("subprojects", projectService.fetchSubprojects(projectID),1);
            return "subprojectsPage";
        } else {
            return "/error";
        }

    }

    @GetMapping("/subprojectsRedirect")
    public RedirectView subprojectRedirect(WebRequest request, int projectID){
        List<Project> lst = (List<Project>) request.getAttribute("projects",1);
        for(Project pro : lst){
            if(pro.getProjectID() == projectID){
                request.setAttribute("parentProject", pro,1);
            }
        }
        return new RedirectView("subprojectsPage");
    }

    @GetMapping("/newSubproject")
    public String newSubproject(){
        return "newSubproject";
    }

    @PostMapping("/createSubproject")
    public RedirectView createSubproject(WebRequest request) throws ExamProjectException {
        String subprojectName = request.getParameter("subprojectName");
        String deadlineDate = request.getParameter("deadline");

        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dt, formatter);

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Project project = (Project) request.getAttribute("parentProject", WebRequest.SCOPE_SESSION);
        assert user != null;
        assert project != null;
        SubProject subProject = projectService.createSubproject(subprojectName, project.getProjectID(), user.getUserID(), localDateTime);
        request.setAttribute("subproject", subProject, WebRequest.SCOPE_SESSION);
        //need to figure out a way to return to the last visited page, something about "referer" maybe?
        return new RedirectView("subprojectsPage");
    }



}
