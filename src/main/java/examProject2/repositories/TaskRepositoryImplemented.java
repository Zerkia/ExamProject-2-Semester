package examProject2.repositories;

import examProject2.domain.models.SubProject;
import examProject2.domain.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImplemented implements TaskRepository{

    public List<Task> fetchTasks(int subprojectID) {
        List<Task> list = new ArrayList<>();

        try {
            String sqlStr = "SELECT * FROM tasks " +
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
                        rs.getDate("deadline")
                );
                list.add(task);
            }
        } catch (SQLException subfetchErr) {
            System.out.println("Error in subFetch");
            System.out.println(subfetchErr.getMessage());
        }
        return list;
    }
}
