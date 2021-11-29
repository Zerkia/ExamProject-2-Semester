package examProject2.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.models.User;


public class UserRepositoryImplemented implements UserRepository{

    public User createUser(User user) throws ExamProjectException {
        try {
            String sqlStr = "INSERT INTO users(username, password, userroleID) VALUES (?, ?, 3)";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

            return user;
        } catch (SQLException regErr) {
            System.out.println("Error in creating user");
            throw new ExamProjectException(regErr.getMessage());
        }
    }

    public User login(String username, String password) throws ExamProjectException {
        try{
            Connection conn = DBManager.getConnection();
            String sqlStr = "SELECT userID, userroleID FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int userID = rs.getInt("userID");
                int userroleID = rs.getInt("userroleID");
                User user = new User(username, password);
                user.setUserID(userID);
                user.setUserroleID(userroleID);
                return user;
            } else {
                throw new ExamProjectException("Could not figure out userID and/or userroleID");
            }
        } catch (SQLException loginErr) {
            System.out.println("Error in logging in");
            throw new ExamProjectException(loginErr.getMessage());
        }
    }

    public List<Project> fetchProjects(User user) {
        List<Project> project = new ArrayList<>();
        int userID = user.getUserID();

        try{
            String sqlStr = "SELECT users.username, projects.* FROM projects " +
                    "INNER JOIN users ON users.userID = projects.userID WHERE users.userID = ?";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);

            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Project list = new Project(
                        rs.getString("projectName"),
                        rs.getString("username"),
                        rs.getInt("projectID"),
                        rs.getDate("deadline")
                );
                project.add(list);
            }
        } catch (SQLException wlErr) {
            System.out.println("Something went wrong");
            System.out.println(wlErr.getMessage());
        }
        return project;
    }

    public List<Project> fetchAllProjects() {
        List<Project> project = new ArrayList<>();

        try{
            String sqlStr = "SELECT users.username, projects.* FROM projects " +
                    "INNER JOIN users ON users.userID = projects.userID";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Project list = new Project(
                        rs.getString("projectName"),
                        //Inner joined username to show the name of who created the project
                        rs.getString("username"),
                        rs.getInt("projectID"),
                        rs.getDate("deadline")
                );
                project.add(list);
            }
            //return wishlist;
        } catch (SQLException wlErr) {
            System.out.println("Something went wrong");
            System.out.println(wlErr.getMessage());
        }
        return project;
    }
}
