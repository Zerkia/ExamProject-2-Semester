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
}
