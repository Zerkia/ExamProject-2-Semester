package examProject2.web;

import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;
import examProject2.domain.services.TaskService;
import examProject2.repositories.TaskRepositoryImplemented;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService = new TaskService(new TaskRepositoryImplemented());

    @GetMapping("/tasksPage")
    public String tasksPage(Model model, WebRequest request){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;
        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        SubProject owner = (SubProject) request.getAttribute("parentSubProject",1);
        assert owner != null;
        String ownerID = owner.getSubprojectOwner();
        System.out.println(request.getAttributeNames(1));
        int subprojectID = owner.getSubprojectID();
        if(username.equals(ownerID)) {
            model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
            return "tasksPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
            return "tasksPage";
        } else {
            return "/error";
        }

    }

    @GetMapping("/tasksRedirect")
    public RedirectView taskRedirect(WebRequest request, int subprojectID){
        System.out.println(request.getAttributeNames(1));
        List<SubProject> lst = (List<SubProject>) request.getAttribute("subprojects",1);
        for(SubProject subPro : lst){
            if(subPro.getSubprojectID() == subprojectID){
                request.setAttribute("parentSubProject", subPro,1);
            }
        }
        return new RedirectView("tasksPage");
    }

    @GetMapping("/subtasksPage")
    public String subtasksPage(Model model, @RequestParam int taskID){
        model.addAttribute("subtasks", taskService.fetchSubTasks(taskID));
        return "subtasksPage";
    }
}
