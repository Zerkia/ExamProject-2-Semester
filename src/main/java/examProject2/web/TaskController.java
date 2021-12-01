package examProject2.web;

import examProject2.domain.models.*;
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
        int subprojectID = owner.getSubprojectID();
        if(username.equals(ownerID)) {
            model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
            request.setAttribute("tasks", taskService.fetchTasks(subprojectID),1);
            return "tasksPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
            request.setAttribute("tasks", taskService.fetchTasks(subprojectID),1);
            return "tasksPage";
        } else {
            return "/error";
        }
    }

    @GetMapping("/tasksRedirect")
    public RedirectView taskRedirect(WebRequest request, int subprojectID){
        List<SubProject> lst = (List<SubProject>) request.getAttribute("subprojects",1);
        for(SubProject subPro : lst){
            if(subPro.getSubprojectID() == subprojectID){
                request.setAttribute("parentSubProject", subPro,1);
            }
        }
        return new RedirectView("tasksPage");
    }

    @GetMapping("/subtasksPage")
    public String subtasksPage(Model model, WebRequest request){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;
        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        Task owner = (Task) request.getAttribute("parentTask",1);
        assert owner != null;
        String ownerID = owner.getTaskOwner();
        int taskID = owner.getTaskID();

        if(username.equals(ownerID)) {
            model.addAttribute("subtasks", taskService.fetchSubTasks(taskID));
            return "subtasksPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("subtasks", taskService.fetchSubTasks(taskID));

            return "subtasksPage";
        } else {
            return "/error";
        }
    }

    @GetMapping("/subtasksRedirect")
    public RedirectView subtaskRedirect(WebRequest request, int taskID){
        List<Task> lst = (List<Task>) request.getAttribute("tasks",1);
        for(Task task : lst){
            if(task.getTaskID() == taskID){
                request.setAttribute("parentTask", task,1);
            }
        }
        return new RedirectView("subtasksPage");
    }

}
