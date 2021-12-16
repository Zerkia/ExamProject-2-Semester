package examProject2;
import examProject2.domain.ExamProjectException;
import examProject2.domain.models.*;
import examProject2.domain.services.ProjectService;
import examProject2.domain.services.TaskService;
import examProject2.domain.services.UserService;
import examProject2.repositories.ProjectRepositoryImplemented;
import examProject2.repositories.TaskRepositoryImplemented;
import examProject2.repositories.UserRepositoryImplemented;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskTests {
    @Test
    void contextLoads() {
    }

    @Test
    void fetchTaskTest() throws ExamProjectException {
        TaskService taskService = new TaskService(new TaskRepositoryImplemented());

        List<Task> tasks = taskService.fetchTasks(1);
        assertThat(tasks.size()).isEqualTo(2);
    }


    @Test
    void fetchSubTaskTest() throws ExamProjectException {
        TaskService taskService = new TaskService(new TaskRepositoryImplemented());

        List<SubTask> subTasks = taskService.fetchSubtasks(1);
        assertThat(subTasks.size()).isEqualTo(2);
    }
    @Test
    void updateTaskTest() {
        TaskService taskService = new TaskService(new TaskRepositoryImplemented());
        List<Task> tasks = taskService.fetchTasks(1);
        Task task = tasks.get(0);
        taskService.updateTask(task,"navneterskiftetnu","2059-02-02T20:20");
        List<Task> tasks1 = taskService.fetchTasks(1);
        assertThat(tasks.get(0).getTaskName()).isNotEqualTo(tasks1.get(0).getTaskName());
        assertThat(tasks.get(0).getDeadline()).isNotEqualTo(tasks1.get(0).getDeadline());
    }
    @Test
    void updateSubTaskTest() {
        TaskService taskService = new TaskService(new TaskRepositoryImplemented());
        List<SubTask> subTasks = taskService.fetchSubtasks(1);
        SubTask subTask = subTasks.get(0);
        taskService.updateSubtask(subTask.getSubtaskID(),"navneterskiftetnu",200,20);
        List<SubTask> subTasks1 = taskService.fetchSubtasks(1);
        assertThat(subTasks.get(0).getSubtaskName()).isNotEqualTo(subTasks1.get(0).getSubtaskName());
        assertThat(subTasks.get(0).getHours()).isNotEqualTo(subTasks1.get(0).getHours());
    }

    @Test
    void addAndDeleteTasksTest() throws ExamProjectException{
        TaskRepositoryImplemented repo = new TaskRepositoryImplemented();

        TaskService taskService = new TaskService(repo);

        List<Task> tasks = taskService.fetchTasks(5);
        taskService.createTask("UnitTest424242424242", 4,5, "2022-03-22T22:22");

        //fetch new wishlist and assert it is larger in size than the initial list
        List<Task> tasks1 = taskService.fetchTasks(5);
        int i = tasks.size();
        assertThat(i).isLessThan(tasks1.size());

        //delete item, fetch updated wishlist, assert it is equal to initial
        //wishlist in size
        taskService.deleteTask(tasks1.get(i-1).getTaskID());
        List<Task> tasks2 = taskService.fetchTasks(5);

        assertThat(tasks2.size()).isEqualTo(tasks.size());

    }

    @Test
    void addAndDeleteSubTasksTest() throws ExamProjectException{
        TaskRepositoryImplemented repo = new TaskRepositoryImplemented();

        TaskService taskService = new TaskService(repo);

        List<SubTask> subTasks = taskService.fetchSubtasks(3);
        taskService.createSubtask("UnitTest42subtask42", 4,3, 4,40);

        List<SubTask> subTasks1 = taskService.fetchSubtasks(3);
        int i = subTasks.size();
        assertThat(i).isLessThan(subTasks1.size());

        taskService.deleteSubtask(subTasks1.get(i-1).getSubtaskID());
        List<SubTask> subTasks2 = taskService.fetchSubtasks(3);

        assertThat(subTasks2.size()).isEqualTo(subTasks.size());

    }
}
