package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.SubProject;
import examProject2.domain.models.User;

import java.sql.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImplemented implements ProjectRepository{
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */

    public Project createProject(Project project) throws ExamProjectException {
        try {
            int userID = project.getUserID();
            String projectName = project.getProjectName();
            LocalDateTime deadline = project.getDeadline();
            String sqlStr = "INSERT INTO projects(userID, projectName, deadline) VALUES (?, ?, ?);";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, userID);
            ps.setString(2, projectName);
            ps.setObject(3, deadline);
            ps.executeUpdate();

            return project;

        } catch (SQLException regErr) {
            System.out.println("Error in creating project");
            throw new ExamProjectException(regErr.getMessage());
        }
    }

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
                        //If userID is connected to a non existent user, project won't show for admins
                        rs.getString("username"),
                        rs.getInt("projectID"),
                        rs.getObject("deadline", LocalDateTime.class),
                        rs.getInt("days"),
                        rs.getInt("hours")
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
                        rs.getObject("deadline", LocalDateTime.class),
                        rs.getInt("days"),
                        rs.getInt("hours")
                );
                list.add(project);
            }
        } catch (SQLException wlErr) {
            System.out.println("Something went wrong");
            System.out.println(wlErr.getMessage());
        }
        return list;
    }

    public String updateProject(int projectID, String projectName, LocalDateTime deadline) {
        try {
            String sqlStr = "UPDATE projects SET projectName = ?, deadline = ? WHERE projectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, projectName);
            ps.setObject(2, deadline);
            ps.setInt(3, projectID);
            ps.executeUpdate();
        } catch (SQLException editErr) {
            System.out.println("Error in editing");
            System.out.println(editErr.getMessage());
        }
        return "mainPage";
    }

    public String deleteProject(int projectID) {
        try{
            String sqlStr = "DELETE projects.* FROM projects WHERE projectID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, projectID);
            ps.executeUpdate();
        } catch (SQLException delErr) {
            System.out.println("Couldn't delete item, Error");
            System.out.println(delErr.getMessage());
        }
        return "redirect:/mainPage";
    }

    //space between main and sub

    public SubProject createSubproject(SubProject subProject) throws ExamProjectException {
        try {
            int userID = subProject.getUserID();
            int projectID = subProject.getProjectID();
            String subprojectName = subProject.getSubprojectName();
            LocalDateTime deadline = subProject.getDeadline();
            String sqlStr = "INSERT INTO subprojects(projectID, userID, subprojectName, deadline) VALUES (?, ?, ?, ?)";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, projectID);
            ps.setInt(2, userID);
            ps.setString(3, subprojectName);
            ps.setObject(4, deadline);
            ps.executeUpdate();

            return subProject;
        } catch (SQLException regErr) {
            System.out.println("Error in creating Subproject");
            throw new ExamProjectException(regErr.getMessage());
        }
    }

    public List<SubProject> fetchSubProjects(int projectID) {
        List<SubProject> list = new ArrayList<>();

        try {
            String sqlStr = "SELECT users.username, subprojects.* FROM subprojects " +
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
                        rs.getObject("deadline", LocalDateTime.class),
                        rs.getInt("days"),
                        rs.getInt("hours")
                );
                list.add(subProject);
            }
        } catch (SQLException subfetchErr) {
            System.out.println("Error in subFetch");
            System.out.println(subfetchErr.getMessage());
        }
        return list;
    }

    public String updateSubproject(int subprojectID, String subprojectName, LocalDateTime deadline) {
        try {
            String sqlStr = "UPDATE subprojects SET subprojectName = ?, deadline = ? WHERE subprojectID = ?;";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, subprojectName);
            ps.setObject(2, deadline);
            ps.setInt(3, subprojectID);
            ps.executeUpdate();
        } catch (SQLException editErr) {
            System.out.println("Error in editing");
            System.out.println(editErr.getMessage());
        }
        return "subprojectsPage";
    }

    public String deleteSubproject(int subprojectID) {
        try {
            String sqlStr = "DELETE subprojects.* FROM subprojects WHERE subprojectID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setInt(1, subprojectID);
            ps.executeUpdate();
        } catch (SQLException delErr) {
            System.out.println("Couldn't delete item, Error");
            System.out.println(delErr.getMessage());
        }
        return "redirect:/subprojectsPage";
    }
}
