package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;
import org.apache.tomcat.jni.Local;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImplemented implements TaskRepository{

    public Task createTask(Task task) throws ExamProjectException {
        try {
            int userID = task.getUserID();
            int subProjectID = task.getSubprojectID();
            String taskName = task.getTaskName();
            LocalDateTime deadline = task.getDeadline();
            String sqlStr = "INSERT INTO tasks(subprojectID, userID, taskName, deadline) VALUES (?, ?, ?, ?)";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, subProjectID);
            ps.setInt(2, userID);
            ps.setString(3, taskName);
            ps.setObject(4, deadline);
            ps.executeUpdate();

            return task;
        } catch (SQLException regErr) {
            System.out.println("Error in creating Subproject");
            throw new ExamProjectException(regErr.getMessage());
        }
    }

    public List<Task> fetchTasks(int subprojectID) {
        List<Task> list = new ArrayList<>();

        try {
            String sqlStr = "SELECT users.username, tasks.* FROM tasks " +
                    "INNER JOIN users ON users.userID = tasks.userID WHERE subprojectID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, subprojectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getString("taskName"),
                        rs.getString("username"),
                        rs.getInt("taskID"),
                        rs.getObject("deadline", LocalDateTime.class)
                );
                list.add(task);
            }
        } catch (SQLException subfetchErr) {
            System.out.println("Error in fetch");
            System.out.println(subfetchErr.getMessage());
        }
        return list;
    }

    public String updateTask(int taskID, String taskName, LocalDateTime deadline) {
        try {
            String sqlStr = "UPDATE tasks SET taskName = ?, deadline = ? WHERE taskID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, taskName);
            ps.setObject(2, deadline);
            ps.setInt(3, taskID);
            ps.executeUpdate();
        } catch (SQLException editErr) {
            System.out.println("Error in editing");
            System.out.println(editErr.getMessage());
        }
        return "tasksPage";
    }

    public String deleteTask(int taskID) {
        try {
            String sqlStr = "Delete tasks.* FROM tasks WHERE taskID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, taskID);
            ps.executeUpdate();
        } catch (SQLException delErr) {
            System.out.println("Couldn't delete item, Error");
            System.out.println(delErr.getMessage());
        }
        return "redirect:/tasksPage";
    }

    //space between main and sub

    public SubTask createSubtask(SubTask subTask) throws ExamProjectException {
        try {
            int userID = subTask.getUserID();
            int taskID = subTask.getTaskID();
            String subtaskName = subTask.getSubtaskName();
            int hours = subTask.getHours();
            int minutes = subTask.getMinutes();
            String sqlStr = "INSERT INTO subtasks(taskID, userID, subtaskName, hours, minutes) VALUES (?, ?, ?, ?, ?)";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, taskID);
            ps.setInt(2, userID);
            ps.setString(3, subtaskName);
            ps.setInt(4, hours);
            ps.setInt(5, minutes);
            ps.executeUpdate();

            return subTask;
        } catch (SQLException regErr) {
            System.out.println("Error in creating Subtask");
            throw new ExamProjectException(regErr.getMessage());
        }
    }

    public List<SubTask> fetchSubtasks(int taskID) {
        List<SubTask> list = new ArrayList<>();

        try {
            String sqlStr = "SELECT users.username, subtasks.* FROM subtasks " +
                    "INNER JOIN users ON users.userID = subtasks.userID WHERE taskID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SubTask subTask = new SubTask(
                        rs.getString("subtaskName"),
                        rs.getString("username"),
                        rs.getInt("subtaskID"),
                        rs.getInt("hours"),
                        rs.getInt("minutes")
                );
                list.add(subTask);
            }
        } catch (SQLException subfetchErr) {
            System.out.println("Error in subFetch");
            System.out.println(subfetchErr.getMessage());
        }
        return list;
    }

    public String updateSubtask(int subtaskID, String subtaskName, int hours, int minutes) {
        try {
            String sqlStr = "UPDATE subtasks SET subtaskName = ?, hours = ?, minutes = ? WHERE subtaskID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, subtaskName);
            ps.setInt(2, hours);
            ps.setInt(3, minutes);
            ps.setInt(4, subtaskID);
            ps.executeUpdate();
        } catch (SQLException editErr) {
            System.out.println("Error in editing");
            System.out.println(editErr.getMessage());
        }
        return "subtasksPage";
    }

    public String deleteSubtask(int subtaskID) {
        try {
            String sqlStr = "DELETE subtasks.* FROM subtasks WHERE subtaskID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, subtaskID);
            ps.executeUpdate();
        } catch(SQLException delErr) {
            System.out.println("Couldn't delete item, Error");
            System.out.println(delErr.getMessage());
        }
        return "redirect:/subtasksPage";
    }
}
