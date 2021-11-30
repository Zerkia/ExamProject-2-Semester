package examProject2.domain.services;

import examProject2.domain.models.SubProject;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;
import examProject2.repositories.TaskRepository;

import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){ this.taskRepository = taskRepository; }
    public List<Task> fetchTasks(int subprojectID){ return taskRepository.fetchTasks(subprojectID); }

    public List<SubTask> fetchSubTasks(int taskID){ return taskRepository.fetchSubTasks(taskID); }
}
