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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @GetMapping("/editProjectRedirect")
    public RedirectView editProjectRedirect(int projectID, WebRequest request, Model model) {
        List<Project> lst = (List<Project>) request.getAttribute("projects",1);
        for(Project pro : lst){
            if(pro.getProjectID() == projectID){
                request.setAttribute("projectInEditing", pro,1);
                model.addAttribute("projectInEditing", pro);
            }
        }
        //needs to use model and potentially webrequest due to ID being in URL
        return new RedirectView("editProject");
    }

    @GetMapping("/editProject")
    public String editProject(WebRequest request, Model model){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;

        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        Project project = (Project) request.getAttribute("projectInEditing",1);
        assert project != null;

        String ownerID = project.getProjectOwner();

        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("projectInEditing", project);
            return "editProject";
        } else if(userRoleID <= 2) {
            model.addAttribute("projectInEditing", project);
            return "editProject";
        } else {
            return "/error500";
        }
    }

    @PostMapping("/updateProject")
    public RedirectView updateProject(WebRequest request) {
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

        String result = projectService.updateProject(projectID, projectName, deadline);
        return new RedirectView(result);
    }

    @GetMapping("/deleteProject")
    public RedirectView deleteProject(int projectID) {
        projectService.deleteProject(projectID);
        return new RedirectView("mainPage");
    }

    //Space between main and subprojects

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

        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("subprojects", projectService.fetchSubprojects(projectID));
            request.setAttribute("subprojects", projectService.fetchSubprojects(projectID),1);
            return "subprojectsPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("subprojects", projectService.fetchSubprojects(projectID));
            request.setAttribute("subprojects", projectService.fetchSubprojects(projectID),1);
            return "subprojectsPage";
        } else {
            return "/error500";
        }
    }

    @GetMapping("/subprojectsRedirect")
    public RedirectView subprojectRedirect(WebRequest request, int projectID){
        List<Project> lst = (List<Project>) request.getAttribute("projects",1);
        assert lst != null;
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

    @GetMapping("/editSubprojectRedirect")
    public RedirectView editSubprojectRedirect(int subprojectID, WebRequest request, Model model) {
        List<SubProject> lst = (List<SubProject>) request.getAttribute("subprojects",1);
        for(SubProject subProject : lst){
            if(subProject.getSubprojectID() == subprojectID){
                request.setAttribute("subprojectInEditing", subProject,1);
                model.addAttribute("subprojectInEditing", subProject);
            }
        }
        //needs to use model and potentially webrequest due to ID being in URL
        return new RedirectView("editSubproject");
    }

    @GetMapping("/editSubproject")
    public String editSubproject(WebRequest request, Model model){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;

        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        SubProject subProject = (SubProject) request.getAttribute("subprojectInEditing",1);
        assert subProject != null;

        String ownerID = subProject.getSubprojectOwner();

        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("subprojectInEditing", subProject);
            return "editSubproject";
        } else if(userRoleID <= 2) {
            model.addAttribute("subprojectInEditing", subProject);
            return "editSubproject";
        } else {
            return "/error500";
        }
    }

    @PostMapping("/updateSubproject")
    public RedirectView updateSubproject(WebRequest request) {
        String subprojectName = request.getParameter("subprojectName");
        String deadlineDate = request.getParameter("deadline");
        SubProject subProject = (SubProject) request.getAttribute("subprojectInEditing",1);
        assert subProject != null;
        int subprojectID = subProject.getSubprojectID();

        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dt, formatter);

        String result = projectService.updateSubproject(subprojectID, subprojectName, deadline);
        return new RedirectView(result);
    }
}
