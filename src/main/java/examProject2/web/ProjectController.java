package examProject2.web;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.*;
import examProject2.domain.services.ProjectService;
import examProject2.domain.services.TaskService;
import examProject2.repositories.ProjectRepositoryImplemented;
import examProject2.repositories.TaskRepositoryImplemented;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

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
            model.addAttribute("hidden", false);
        } else {
            model.addAttribute("projects", projectService.fetchProjects(user));
            request.setAttribute("projects", projectService.fetchProjects(user), 1);
            model.addAttribute("hidden", true);
        }

        return "mainPage";
    }

    @GetMapping("/newProject")
    public String newProject(){return "newProject";}

    @PostMapping("/createProject")
    public RedirectView createProject(WebRequest request) throws ExamProjectException {
        String projectname = request.getParameter("projectName");
        String deadlineString = request.getParameter("deadline");

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        assert user != null;
        Project project = projectService.createProject(projectname, user.getUserID(),deadlineString);
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
        String deadlineString = request.getParameter("deadline");
        Project project = (Project) request.getAttribute("projectInEditing", 1);
        assert project != null;
        String result = projectService.updateProject(project, projectName, deadlineString);
        return new RedirectView(result);
    }

    @GetMapping("/deleteProject")
    public RedirectView deleteProject(int projectID) {
        projectService.deleteProject(projectID);
        return new RedirectView("mainPage");
    }

    //Space between main and subprojects

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

    @GetMapping("/newSubproject")
    public String newSubproject(){
        return "newSubproject";
    }

    @PostMapping("/createSubproject")
    public RedirectView createSubproject(WebRequest request) throws ExamProjectException {
        String subprojectName = request.getParameter("subprojectName");
        String deadlineString = request.getParameter("deadline");


        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Project project = (Project) request.getAttribute("parentProject", WebRequest.SCOPE_SESSION);
        assert user != null;
        assert project != null;
        SubProject subProject = projectService.createSubproject(subprojectName, project.getProjectID(), user.getUserID(), deadlineString);
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

    @GetMapping("/deleteSubproject")
    public RedirectView deleteSubproject(int subprojectID, WebRequest request) {
        int hours = 0;
        TaskService taskService = new TaskService(new TaskRepositoryImplemented());
        Project project = (Project) request.getAttribute("parentProject", 1);
        List<Task> taskList = taskService.fetchTasks(subprojectID);
        for(Task task : taskList){
            List<SubTask> subTaskList = taskService.fetchSubtasks(task.getTaskID());
            for(SubTask subTask : subTaskList) {
                hours = subTask.getHours();
                Project project1 = taskService.updateProjectTimeDeleteSubtask(project, hours);
                request.setAttribute("parentProject", project1, 1);
            }
        }

        projectService.deleteSubproject(subprojectID);
        return new RedirectView("subprojectsPage");
    }
}
