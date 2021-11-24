package examProject2.repositories;

import java.sql.*;

import examProject2.domain.ExamProjectException;
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
            String sqlStr = "SELECT userID FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sqlStr);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int userID = rs.getInt("userID");
                User user = new User(username, password);
                user.setUserID(userID);
                return user;
            } else {
                throw new ExamProjectException("Could not figure out userID");
            }
        } catch (SQLException loginErr) {
            System.out.println("Error in logging in");
            throw new ExamProjectException(loginErr.getMessage());
        }
    }
}
