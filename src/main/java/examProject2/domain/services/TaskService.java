package examProject2.domain.services;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;
import examProject2.repositories.TaskRepository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){ this.taskRepository = taskRepository; }
    public List<Task> fetchTasks(int subprojectID){ return taskRepository.fetchTasks(subprojectID); }

    public List<SubTask> fetchSubTasks(int taskID){ return taskRepository.fetchSubTasks(taskID); }

    public Task createTask(String taskName, int userID, int subprojectID, LocalDateTime deadline) throws ExamProjectException {
        Task task = new Task(taskName,userID,subprojectID,deadline);
        return taskRepository.createTask(task);
    }

    public SubTask createSubTask(String subtaskName, int userID, int taskID, int hours, int minutes) throws ExamProjectException{
        SubTask subTask = new SubTask(subtaskName, userID, taskID, hours, minutes);
        return taskRepository.createSubTask(subTask);
    }
}
