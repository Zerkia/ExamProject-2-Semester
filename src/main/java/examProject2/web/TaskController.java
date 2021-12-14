package examProject2.web;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.*;
import examProject2.domain.services.TaskService;
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
public class TaskController {
    private TaskService taskService = new TaskService(new TaskRepositoryImplemented());

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
        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
            request.setAttribute("tasks", taskService.fetchTasks(subprojectID),1);
            return "tasksPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("tasks", taskService.fetchTasks(subprojectID));
            request.setAttribute("tasks", taskService.fetchTasks(subprojectID),1);
            return "tasksPage";
        } else {
            return "/error500";
        }
    }

    @GetMapping("/newTask")
    public String newTask(){
        return "newTask";
    }

    @PostMapping("/createTask")
    public RedirectView createTask(WebRequest request) throws ExamProjectException {
        String taskName = request.getParameter("taskName");
        String deadlineDate = request.getParameter("deadline");

        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dt, formatter);

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        SubProject subProject = (SubProject) request.getAttribute("parentSubProject", WebRequest.SCOPE_SESSION);
        assert user != null;
        assert subProject != null;
        Task task = taskService.createTask(taskName, user.getUserID(), subProject.getSubprojectID(), localDateTime);
        request.setAttribute("task", task, WebRequest.SCOPE_SESSION);
        //need to figure out a way to return to the last visited page, something about "referer" maybe?
        return new RedirectView("tasksPage");
    }

    @GetMapping("/editTaskRedirect")
    public RedirectView editTaskRedirect(int taskID, WebRequest request, Model model) {
        List<Task> lst = (List<Task>) request.getAttribute("tasks", 1);
        for(Task task : lst){
            if(task.getTaskID() == taskID) {
                request.setAttribute("taskInEditing", task, 1);
                model.addAttribute("taskInEditing", task);
            }
        }
        return new RedirectView("editTask");
    }

    @GetMapping("/editTask")
    public String editTask(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", 1);
        assert user != null;

        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        Task task = (Task) request.getAttribute("taskInEditing", 1);
        assert task != null;

        String ownerID = task.getTaskOwner();

        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("taskInEditing", task);
            return "editTask";
        } else if(userRoleID <= 2) {
            model.addAttribute("taskInEditing", task);
            return "editTask";
        } else {
            return "/error500";
        }
    }

    @PostMapping("/updateTask")
    public RedirectView updateTask(WebRequest request) {
        String taskName = request.getParameter("taskName");
        String deadlineDate = request.getParameter("deadline");
        Task task = (Task) request.getAttribute("taskInEditing", 1);
        assert task != null;
        int taskID = task.getTaskID();

        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dt, formatter);

        String result = taskService.updateTask(taskID, taskName, deadline);
        return new RedirectView(result);
    }

    @GetMapping("/deleteTask")
    public RedirectView deleteTask(int taskID, WebRequest request){
        int hours = 0;
        Project project = (Project) request.getAttribute("parentProject", 1);
        List<SubTask> lst = taskService.fetchSubtasks(taskID);
        System.out.println(lst.get(0).getSubtaskName());
        SubProject subProject = (SubProject) request.getAttribute("parentSubProject", WebRequest.SCOPE_SESSION);

        for(SubTask subTask : lst){
            hours = subTask.getHours();
            System.out.println(hours);
            taskService.updateSubProjectTimeDeleteSubtask(subProject, hours);
            taskService.updateProjectTimeDeleteSubtask(project, hours);
        }
        taskService.deleteTask(taskID);
        return new RedirectView("tasksPage");
    }

    //Space between main and sub

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

        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("subtasks", taskService.fetchSubtasks(taskID));
            request.setAttribute("subtasks", taskService.fetchSubtasks(taskID),1);
            return "subtasksPage";
        } else if(userRoleID <= 2) {
            model.addAttribute("subtasks", taskService.fetchSubtasks(taskID));
            request.setAttribute("subtasks", taskService.fetchSubtasks(taskID),1);
            return "subtasksPage";
        } else {
            return "/error500";
        }
    }

    @GetMapping("/newSubtask")
    public String newSubtask(){
        return "newSubtask";
    }

    @PostMapping("/createSubTask")
    public RedirectView createSubtask(WebRequest request, Model model) throws ExamProjectException {
        String subtaskName = request.getParameter("subtaskName");
        int hours = Integer.valueOf(request.getParameter("hoursRequired"));
        int minutes = Integer.valueOf(request.getParameter("minutesRequired"));

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Task task = (Task) request.getAttribute("parentTask", WebRequest.SCOPE_SESSION);
        assert user != null;
        assert task != null;
        SubTask subTask = taskService.createSubtask(subtaskName, user.getUserID(), task.getTaskID(), hours, minutes);
        request.setAttribute("subtask", subTask, WebRequest.SCOPE_SESSION);

        Task newTask = taskService.updateTaskTimeCreateSubtask(task, hours);
        request.setAttribute("parentTask", newTask, 1);

        SubProject subProject = (SubProject) request.getAttribute("parentSubProject", WebRequest.SCOPE_SESSION);
        SubProject updatedSubProject = taskService.updateSubProjectTimeCreateSubtask(subProject, hours);
        request.setAttribute("parentSubProject", updatedSubProject, 1);

        Project project = (Project) request.getAttribute("parentProject", WebRequest.SCOPE_SESSION);
        Project projectUpdated = taskService.updateProjectTimeCreateSubtask(project, hours);
        request.setAttribute("parentProject", projectUpdated, 1);

        //need to figure out a way to return to the last visited page, something about "referer" maybe?
        return new RedirectView("subtasksPage");
    }

    @GetMapping("/editSubtaskRedirect")
    public RedirectView editSubtaskRedirect(int subtaskID, WebRequest request, Model model) {
        List<SubTask> lst = (List<SubTask>) request.getAttribute("subtasks",1);
        for(SubTask subTask : lst){
            if(subTask.getSubtaskID() == subtaskID){
                request.setAttribute("subtaskInEditing", subTask,1);
                model.addAttribute("subtaskInEditing", subTask);
            }
        }
        return new RedirectView("editSubtask");
    }

    @GetMapping("/editSubtask")
    public String editSubtask(WebRequest request, Model model){
        User user = (User) request.getAttribute("user", 1);
        assert user != null;

        String username = user.getUsername();
        int userRoleID = user.getUserroleID();
        SubTask subTask = (SubTask) request.getAttribute("subtaskInEditing",1);
        assert subTask != null;

        String ownerID = subTask.getSubtaskOwner();

        if(username.equalsIgnoreCase(ownerID)) {
            model.addAttribute("subtaskInEditing", subTask);
            return "editSubtask";
        } else if(userRoleID <= 2) {
            model.addAttribute("subtaskInEditing", subTask);
            return "editSubtask";
        } else {
            return "/error500";
        }
    }

    @PostMapping("/updateSubtask")
    public RedirectView updateSubtask(WebRequest request) {
        String subtaskName = request.getParameter("subtaskName");
        int hours = Integer.valueOf(request.getParameter("hoursRequired"));
        int minutes = Integer.valueOf(request.getParameter("minutesRequired"));

        SubTask subTask = (SubTask) request.getAttribute("subtaskInEditing",1);
        assert subTask != null;
        int subtaskID = subTask.getSubtaskID();
        int oldHours = subTask.getHours();

        String result = taskService.updateSubtask(subtaskID, subtaskName, hours, minutes);

        Task task = (Task) request.getAttribute("parentTask", WebRequest.SCOPE_SESSION);
        Task taskUpdated = taskService.updateTaskTimeUpdateSubtask(task, hours, oldHours);
        request.setAttribute("parentTask", taskUpdated, 1);

        SubProject subProject = (SubProject) request.getAttribute("parentSubProject", WebRequest.SCOPE_SESSION);
        SubProject subProjectUpdated = taskService.updateSubProjectTimeUpdateSubtask(subProject, hours, oldHours);
        request.setAttribute("parentSubProject", subProjectUpdated, 1);

        Project project = (Project) request.getAttribute("parentProject", WebRequest.SCOPE_SESSION);
        Project projectUpdated = taskService.updateProjectTimeUpdateSubtask(project, hours, oldHours);
        request.setAttribute("parentProject", projectUpdated, 1);

        return new RedirectView(result);
    }

    @GetMapping("/deleteSubtask")
    public RedirectView deleteSubtask(int subtaskID, WebRequest request) {
        int hours = 0;
        Project project = (Project) request.getAttribute("parentProject", 1);
        List<SubTask> lst = (List<SubTask>) request.getAttribute("subtasks",1);
        for(SubTask subTask : lst){
            if(subTask.getSubtaskID() == subtaskID){
                hours = subTask.getHours();
            }
        }
        Task task = (Task) request.getAttribute("parentTask", 1);
        SubProject subProject = (SubProject) request.getAttribute("parentSubProject", 1);
        Task taskUpdated = taskService.updateTaskTimeDeleteSubtask(task, hours);
        request.setAttribute("parentTask", taskUpdated, 1);
        SubProject subProjectUpdated = taskService.updateSubProjectTimeDeleteSubtask(subProject, hours);
        request.setAttribute("parentSubProject", subProjectUpdated, 1);
        Project projectUpdated =  taskService.updateProjectTimeDeleteSubtask(project, hours);
        request.setAttribute("parentProject", projectUpdated, 1);
        taskService.deleteSubtask(subtaskID);
        return new RedirectView("subtasksPage");
    }
}
