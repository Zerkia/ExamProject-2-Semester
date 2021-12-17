package examProject2.domain.services;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;
import examProject2.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskService {
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */
    private TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){ this.taskRepository = taskRepository; }

    public Task createTask(String taskName, int userID, int subprojectID, String deadlineDate) throws ExamProjectException {
        String date = deadlineDate.substring(0,10).concat(" ");
        String time = deadlineDate.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dt, formatter);
        Task task = new Task(taskName,userID,subprojectID, localDateTime);
        return taskRepository.createTask(task);
    }
    public List<Task> fetchTasks(int subprojectID){ return taskRepository.fetchTasks(subprojectID); }
    public String updateTask(Task task, String taskName, String deadlineString) {
        int taskID = task.getTaskID();

        String date = deadlineString.substring(0,10).concat(" ");
        String time = deadlineString.substring(11);
        String dt = date.concat(time);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dt, formatter);
        return taskRepository.updateTask(taskID, taskName, deadline);
    }
    public String deleteTask(int taskID){ return taskRepository.deleteTask(taskID); }
    public Task loopThroughTasks(List<Task> tasks, int taskID){
        for(Task task : tasks){
            if(task.getTaskID() == taskID) {
                return task;
            }
        }
        return null;
    }

    public SubTask createSubtask(String subtaskName, int userID, int taskID, int hours, int minutes) throws ExamProjectException{
        SubTask subTask = new SubTask(subtaskName, userID, taskID, hours, minutes);
        return taskRepository.createSubtask(subTask);
    }
    public List<SubTask> fetchSubtasks(int taskID){ return taskRepository.fetchSubtasks(taskID); }
    public String updateSubtask(int subtaskID, String subtaskName, int hours, int minutes) {
        return taskRepository.updateSubtask(subtaskID, subtaskName, hours, minutes);
    }
    /**
     * @author Mads Haderup
     */
    public String deleteSubtask(int subtaskID) { return taskRepository.deleteSubtask(subtaskID); }

    public Project updateProjectTimeCreateSubtask(Project project, int hours){
        return taskRepository.updateProjectTimeCreateSubtask(project, hours);
    }
    public Project updateProjectTimeUpdateSubtask(Project project, int hours, int oldHours){
        return taskRepository.updateProjectTimeUpdateSubtask(project, hours, oldHours);
    }
    public Project updateProjectTimeDeleteSubtask(Project project, int hours){
        return taskRepository.updateProjectTimeDeleteSubtask(project, hours);
    }

    public SubProject updateSubProjectTimeCreateSubtask(SubProject subProject, int hours){
        return taskRepository.updateSubprojectTimeCreateSubtask(subProject, hours);
    }
    public SubProject updateSubProjectTimeUpdateSubtask(SubProject subProject, int hours, int oldHours){
        return taskRepository.updateSubprojectTimeUpdateSubtask(subProject, hours, oldHours);
    }
    public SubProject updateSubProjectTimeDeleteSubtask(SubProject subProject, int hours){
        return taskRepository.updateSubProjectTimeDeleteSubtask(subProject, hours);
    }

    public Task updateTaskTimeCreateSubtask(Task task, int hours){
        return taskRepository.updateTaskTimeCreateSubtask(task, hours);
    }
    public Task updateTaskTimeUpdateSubtask(Task task, int hours, int oldHours){
        return taskRepository.updateTaskTimeUpdateSubtask(task, hours, oldHours);
    }
    public Task updateTaskTimeDeleteSubtask(Task task, int hours){
        return taskRepository.updateTaskTimeDeleteSubtask(task, hours);
    }

}
