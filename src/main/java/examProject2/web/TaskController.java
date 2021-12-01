package examProject2.web;

import examProject2.domain.services.TaskService;
import examProject2.repositories.TaskRepositoryImplemented;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {
    private TaskService taskService = new TaskService(new TaskRepositoryImplemented());

    @GetMapping("/tasksPage")
    public String tasksPage(Model model, @RequestParam int subprojectID){
        model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
        return "tasksPage";
    }

    @GetMapping("/subtasksPage")
    public String subtasksPage(Model model, @RequestParam int taskID){
        model.addAttribute("subtasks", taskService.fetchSubTasks(taskID));
        return "subtasksPage";
    }
}
