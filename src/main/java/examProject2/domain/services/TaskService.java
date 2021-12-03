package examProject2.domain.services;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;
import examProject2.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){ this.taskRepository = taskRepository; }

    public Task createTask(String taskName, int userID, int subprojectID, LocalDateTime deadline) throws ExamProjectException {
        Task task = new Task(taskName,userID,subprojectID,deadline);
        return taskRepository.createTask(task);
    }
    public List<Task> fetchTasks(int subprojectID){ return taskRepository.fetchTasks(subprojectID); }

    //space between main and sub

    public SubTask createSubtask(String subtaskName, int userID, int taskID, int hours, int minutes) throws ExamProjectException{
        SubTask subTask = new SubTask(subtaskName, userID, taskID, hours, minutes);
        return taskRepository.createSubtask(subTask);
    }
    public List<SubTask> fetchSubtasks(int taskID){ return taskRepository.fetchSubtasks(taskID); }
    public String updateSubtask(int subtaskID, String subtaskName, int hours, int minutes) {
        return taskRepository.updateSubtask(subtaskID, subtaskName, hours, minutes);
    }
}
