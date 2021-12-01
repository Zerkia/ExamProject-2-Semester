package examProject2.web;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ProjectController {
    private ProjectService projectService = new ProjectService(new ProjectRepositoryImplemented());

    @GetMapping("/mainPage")
    public String mainPage(Model model, WebRequest request){
        User user = (User) request.getAttribute("user", 1);

        assert user != null;
        if(user.getUserroleID() <= 2){
            model.addAttribute("projects", projectService.fetchAllProjects());
        } else {
            model.addAttribute("projects", projectService.fetchProjects(user));
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

    @GetMapping("/deleteProject")
    public RedirectView deleteProject(int projectID) {
        projectService.deleteProject(projectID);
        return new RedirectView("mainPage");
    }


    @GetMapping("/subprojectsPage")
    public String subprojectsPage(Model model, @RequestParam int projectID, WebRequest request){
        //User user = (User) request.getAttribute("user", 1);
        //Project project = (Project) request.getAttribute("project", 1);

        //assert user != null;
        //assert project != null;
        //if(user.getUserID() != project.getUserID()){
        //    return "errorPage";
        //} else {
            model.addAttribute("subprojects", projectService.fetchSubprojects(projectID));
            return "subprojectsPage";
        //}
    }

    @GetMapping("/newSubproject")
    public String newSubproject(){ return "newSubproject"; }

    @GetMapping("/createSubproject")
    public RedirectView createSubproject(WebRequest request) throws ExamProjectException{
        return new RedirectView("mainPage");
    }

    @GetMapping("/error")
    public String error404(){
        return "error404";
    }
}
