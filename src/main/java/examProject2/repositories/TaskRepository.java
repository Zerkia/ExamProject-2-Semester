package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> fetchTasks(int subProjectID);
    Task createTask(Task task) throws ExamProjectException;
    List<SubTask> fetchSubTasks(int taskID);
}
