package examProject2.repositories;

import examProject2.domain.models.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> fetchTasks(int subProjectID);
}
