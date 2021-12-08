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

    public boolean checkUser(String username) {
        try {
            String sqlStr = "SELECT * FROM users WHERE username = " + username;
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.executeUpdate();
        } catch (SQLException checkErr) {
            System.out.println("Error in checking usernames or username already exists for username: " + username);
        }
        if(username == null) {
            return true;
        } else {
            return false;
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
}
