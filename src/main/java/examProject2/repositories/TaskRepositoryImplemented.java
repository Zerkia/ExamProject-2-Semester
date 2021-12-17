package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.SubTask;
import examProject2.domain.models.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImplemented implements TaskRepository{
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */
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
                        rs.getObject("deadline", LocalDateTime.class),
                        rs.getInt("days"),
                        rs.getInt("hours")
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
    /**
     * @author Mads Haderup
     */
    public Task updateTaskTimeCreateSubtask(Task task, int hours){
        try{
            int extraDays = (hours + task.getHours())/8;
            int extraHoursforReal = (hours + task.getHours())%8;
            Task task1 = new Task(task.getTaskName(),task.getTaskOwner(),task.getTaskID(),task.getDeadline(),
                    task.getDays()+extraDays, extraHoursforReal);
            String sqlStr = "UPDATE tasks SET days = ?, hours = ? WHERE taskID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, task1.getDays());
            ps.setInt(2, task1.getHours());
            ps.setInt(3, task1.getTaskID());
            ps.executeUpdate();
            return task1;
        } catch (SQLException err){
            System.out.println("ErrorHere");
        }
        return task;
    }
    public Task updateTaskTimeUpdateSubtask(Task task, int hours, int oldHours){
        try{
            Task newTask;
            int extraTaskHours = ((hours-oldHours) + task.getHours())%8;
            if(task.getHours()+extraTaskHours < 0){
                int newTaskDays = (((hours-oldHours) + task.getHours())/8)-1;
                int newTaskHours = 8 - extraTaskHours;
                newTask = new Task(task.getTaskName(), task.getTaskOwner(), task.getTaskID(),
                        task.getDeadline(),task.getDays()+ newTaskDays, newTaskHours);
            } else if(extraTaskHours > 7) {
                int newTaskDays = (((hours-oldHours) + task.getHours())/8)+1;
                int newTaskHours = 8 - extraTaskHours;
                newTask = new Task(task.getTaskName(), task.getTaskOwner(), task.getTaskID(),
                        task.getDeadline(),task.getDays()+ newTaskDays,newTaskHours);
            } else {
                int newTaskDays = ((hours-oldHours) + task.getHours())/8;
                newTask = new Task(task.getTaskName(), task.getTaskOwner(), task.getTaskID(),
                        task.getDeadline(),task.getDays()+newTaskDays,extraTaskHours);
            }

            String sqlStr = "UPDATE tasks SET days = ?, hours = ? WHERE taskID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newTask.getDays());
            ps.setInt(2, newTask.getHours());
            ps.setInt(3, newTask.getTaskID());
            ps.executeUpdate();
            return newTask;
        } catch (SQLException err){
            System.out.println("ErrorHere");
        }
        return task;
    }
public Task updateTaskTimeDeleteSubtask(Task task, int hours){
    try {
        Task newTask;
        int days = hours/8;
        int newHours = hours%8;
        if(task.getHours()-newHours < 0){
            int newProDays = days+1;
            int newProHours = 8 - newHours + task.getHours();
            newTask = new Task(task.getTaskName(), task.getTaskOwner(), task.getTaskID(),
                    task.getDeadline(),task.getDays()-newProDays,newProHours);
        } else {
            newTask = new Task(task.getTaskName(), task.getTaskOwner(), task.getTaskID(),
                    task.getDeadline(),task.getDays()-days, task.getHours()-newHours);
        }
        String sqlStr = "UPDATE tasks SET days = ?, hours = ? WHERE taskID = ?;";
        Connection conn = DBManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sqlStr);
        ps.setInt(1, newTask.getDays());
        ps.setInt(2, newTask.getHours());
        ps.setInt(3, newTask.getTaskID());
        ps.executeUpdate();
        return newTask;
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    return task;
}

    public SubProject updateSubprojectTimeCreateSubtask(SubProject subProject, int hours){
        try{
            int extraSubProHours = (hours + subProject.getHours())%8;
            int extraSubProDays = (hours + subProject.getHours())/8;
            SubProject newSubProject = new SubProject(subProject.getSubprojectName(), subProject.getSubprojectOwner(), subProject.getSubprojectID(),
                    subProject.getDeadline(),subProject.getDays()+extraSubProDays,extraSubProHours);
            String sqlStr = "UPDATE subprojects SET days = ?, hours = ? WHERE subprojectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newSubProject.getDays());
            ps.setInt(2, newSubProject.getHours());
            ps.setInt(3, newSubProject.getSubprojectID());
            ps.executeUpdate();
            return newSubProject;
        } catch (SQLException err){
            System.out.println("ErrorHere");
        }
        return subProject;
    }
    public SubProject updateSubprojectTimeUpdateSubtask(SubProject subProject, int hours, int oldhours){
        try{
            SubProject newSubProject;
            int extraSubProHours = ((hours-oldhours) + subProject.getHours())%8;
            if(subProject.getHours()+extraSubProHours < 0){
                int newSubProDays = (((hours-oldhours) + subProject.getHours())/8)-1;
                int newSubProHours = 8 - extraSubProHours;
                newSubProject = new SubProject(subProject.getSubprojectName(), subProject.getSubprojectOwner(), subProject.getSubprojectID(),
                        subProject.getDeadline(),subProject.getDays()+ newSubProDays,newSubProHours);
            } else if(extraSubProHours > 7) {
                int newSubProDays = (((hours-oldhours) + subProject.getHours())/8)+1;
                int newSubProHours = 8 - extraSubProHours;
                newSubProject = new SubProject(subProject.getSubprojectName(), subProject.getSubprojectOwner(), subProject.getSubprojectID(),
                        subProject.getDeadline(),subProject.getDays()+ newSubProDays,newSubProHours);
            } else {
                int extraSubProDays = ((hours-oldhours) + subProject.getHours())/8;
                newSubProject = new SubProject(subProject.getSubprojectName(), subProject.getSubprojectOwner(), subProject.getSubprojectID(),
                        subProject.getDeadline(),subProject.getDays()+extraSubProDays,extraSubProHours);
            }

            String sqlStr = "UPDATE subprojects SET days = ?, hours = ? WHERE subprojectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newSubProject.getDays());
            ps.setInt(2, newSubProject.getHours());
            ps.setInt(3, newSubProject.getSubprojectID());
            ps.executeUpdate();
            return newSubProject;
        } catch (SQLException err){
            System.out.println("ErrorHere");
        }
        return subProject;
    }
    public SubProject updateSubProjectTimeDeleteSubtask(SubProject subProject, int hours){
        try {
            SubProject newSubProject;
            int days = hours/8;
            int newHours = hours%8;
            if(subProject.getHours()-newHours < 0){
                int newProDays = days+1;
                int newProHours = 8 - newHours + subProject.getHours();
                newSubProject = new SubProject(subProject.getSubprojectName(), subProject.getSubprojectOwner(), subProject.getSubprojectID(),
                        subProject.getDeadline(),subProject.getDays()-newProDays,newProHours);
            } else {
                newSubProject = new SubProject(subProject.getSubprojectName(), subProject.getSubprojectOwner(), subProject.getSubprojectID(),
                        subProject.getDeadline(),subProject.getDays()-days, subProject.getHours()-newHours);
            }
            String sqlStr = "UPDATE subprojects SET days = ?, hours = ? WHERE subprojectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newSubProject.getDays());
            ps.setInt(2, newSubProject.getHours());
            ps.setInt(3, newSubProject.getSubprojectID());
            ps.executeUpdate();
            return newSubProject;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subProject;
    }


    public Project updateProjectTimeCreateSubtask(Project project, int hours){
        try{
            int extraProHours = (hours + project.getHours())%8;
            int extraProDays = (hours + project.getHours())/8;
            Project newProject = new Project(project.getProjectName(), project.getProjectOwner(),project.getProjectID(),
                    project.getDeadline(), project.getDays()+extraProDays, extraProHours);
            String sqlStr = "UPDATE projects SET days = ?, hours = ? WHERE projectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newProject.getDays());
            ps.setInt(2, newProject.getHours());
            ps.setInt(3, newProject.getProjectID());
            ps.executeUpdate();
            return newProject;
        } catch (SQLException err){
            System.out.println("ErrorHere");
        }
        return project;
    }
    public Project updateProjectTimeUpdateSubtask(Project project, int hours, int oldHours){
        try{
            Project newProject;
            int extraProHours = ((hours-oldHours) + project.getHours())%8;
            if(project.getHours()+extraProHours < 0){
                int newSubProDays = (((hours-oldHours) + project.getHours())/8)-1;
                int newSubProHours = 8 - extraProHours;
                newProject = new Project(project.getProjectName(), project.getProjectOwner(), project.getProjectID(),
                        project.getDeadline(),project.getDays()+ newSubProDays,newSubProHours);
            } else if(extraProHours > 7) {
                int newProDays = (((hours-oldHours) + project.getHours())/8)+1;
                int newProHours = 8 - extraProHours;
                newProject = new Project(project.getProjectName(), project.getProjectOwner(), project.getProjectID(),
                        project.getDeadline(),project.getDays()+ newProDays,newProHours);
            } else {
                int extraProDays = ((hours-oldHours) + project.getHours())/8;
                newProject = new Project(project.getProjectName(), project.getProjectOwner(), project.getProjectID(),
                        project.getDeadline(),project.getDays()+extraProDays,extraProHours);
            }


            String sqlStr = "UPDATE projects SET days = ?, hours = ? WHERE projectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newProject.getDays());
            ps.setInt(2, newProject.getHours());
            ps.setInt(3, newProject.getProjectID());
            ps.executeUpdate();
            return newProject;
        } catch (SQLException err){
            System.out.println("ErrorHere");
        }
        return project;
    }

    public Project updateProjectTimeDeleteSubtask(Project project, int hours){
        try {
            Project newProject;
            int days = hours/8;
            int newHours = hours%8;
            if(project.getHours()-newHours < 0){
                int newProDays = days+1;
                int newProHours = 8 - newHours + project.getHours();
                newProject = new Project(project.getProjectName(), project.getProjectOwner(), project.getProjectID(),
                        project.getDeadline(),project.getDays()-newProDays,newProHours);
            } else {
                newProject = new Project(project.getProjectName(), project.getProjectOwner(), project.getProjectID(),
                        project.getDeadline(),project.getDays()-days, project.getHours()-newHours);
            }
            String sqlStr = "UPDATE projects SET days = ?, hours = ? WHERE projectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, newProject.getDays());
            ps.setInt(2, newProject.getHours());
            ps.setInt(3, newProject.getProjectID());
            ps.executeUpdate();
            return newProject;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return project;
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
