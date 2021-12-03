package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {
    Task createTask(Task task) throws ExamProjectException;
    List<Task> fetchTasks(int subProjectID);

    //space between main and sub

    SubTask createSubtask(SubTask subTask) throws ExamProjectException;
    List<SubTask> fetchSubtasks(int taskID);
    String updateSubtask(int subtaskID, String subtaskName, int hours, int minutes);

}
