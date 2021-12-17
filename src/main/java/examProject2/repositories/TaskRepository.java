package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */
    Task createTask(Task task) throws ExamProjectException;
    List<Task> fetchTasks(int subProjectID);
    String updateTask(int taskID, String taskName, LocalDateTime deadline);
    String deleteTask(int taskID);


    SubTask createSubtask(SubTask subTask) throws ExamProjectException;
    List<SubTask> fetchSubtasks(int taskID);
    String updateSubtask(int subtaskID, String subtaskName, int hours, int minutes);
    String deleteSubtask(int subtaskID);

    /**
     * @author Mads Haderup
     */

    Project updateProjectTimeCreateSubtask(Project project, int hours);
    SubProject updateSubprojectTimeCreateSubtask(SubProject subProject, int hours);
    Task updateTaskTimeCreateSubtask(Task task, int hours);

    Project updateProjectTimeUpdateSubtask(Project project, int hours, int oldHours);
    SubProject updateSubprojectTimeUpdateSubtask(SubProject subProject, int hours, int oldHours);
    Task updateTaskTimeUpdateSubtask(Task task, int hours, int oldHours);

    Project updateProjectTimeDeleteSubtask(Project project, int hours);
    SubProject updateSubProjectTimeDeleteSubtask(SubProject subProject, int hours);
    Task updateTaskTimeDeleteSubtask(Task task, int hours);

}
