package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImplemented implements ProjectRepository{

    @Override
    public Project createProject(Project project) throws ExamProjectException {

        try {
            int userID = project.getUserID();
            String projectName = project.getProjectName();
            String sqlStr = "INSERT INTO projects(userID, projectName) VALUES (?, ?);";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, userID);
            ps.setString(2, projectName);
            ps.executeUpdate();

            return project;

        } catch (SQLException regErr) {
            System.out.println("Error in creating project");
            throw new ExamProjectException(regErr.getMessage());
        }
    }

    @Override
    public List<Project> fetchProjects(User user) {
        List<Project> list = new ArrayList<>();
        int userID = user.getUserID();

        try{
            String sqlStr = "SELECT users.username, projects.* FROM projects " +
                    "INNER JOIN users ON users.userID = projects.userID WHERE users.userID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);

            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Project project = new Project(
                        rs.getString("projectName"),
                        //Inner joined username to show the name of who created the project
                        //If userID is connected to a non existent user, project won't show
                        rs.getString("username"),
                        rs.getInt("projectID"),
                        rs.getDate("deadline")
                );
                list.add(project);
            }
        } catch (SQLException fetchErr) {
            System.out.println("Something went wrong");
            System.out.println(fetchErr.getMessage());
        }
        return list;
    }

    public List<Project> fetchAllProjects() {
        List<Project> list = new ArrayList<>();

        try{
            String sqlStr = "SELECT users.username, projects.* FROM projects " +
                    "INNER JOIN users ON users.userID = projects.userID";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Project project = new Project(
                        rs.getString("projectName"),
                        rs.getString("username"),
                        rs.getInt("projectID"),
                        rs.getDate("deadline")
                );
                list.add(project);
            }
        } catch (SQLException wlErr) {
            System.out.println("Something went wrong");
            System.out.println(wlErr.getMessage());
        }
        return list;
    }

    public String deleteProject(int projectID) {
        try{
            String sqlStr = "DELETE projects.* FROM projects WHERE projectID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, projectID);
            ps.executeUpdate();

            return "Success!";
        } catch (SQLException delErr) {
            System.out.println("Couldn't delete item, Error");
            System.out.println(delErr.getMessage());
        }
        return "redirect:/mainPage";
    }

    public List<SubProject> fetchSubProjects(int projectID) {
        List<SubProject> list = new ArrayList<>();

        try {
            String sqlStr = "SELECT * FROM subprojects " +
                    "INNER JOIN users ON users.userID = subprojects.userID WHERE projectID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SubProject subProject = new SubProject(
                        rs.getString("subprojectName"),
                        rs.getString("username"),
                        rs.getInt("subprojectID"),
                        rs.getDate("deadline")
                );
                list.add(subProject);
            }
        } catch (SQLException subfetchErr) {
            System.out.println("Error in subFetch");
            System.out.println(subfetchErr.getMessage());
        }
        return list;
    }
}
