package examProject2.repositories;

import java.sql.*;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.User;


public class UserRepositoryImplemented implements UserRepository{

    public User createUser(User user) throws ExamProjectException {

        try {
            String sqlStr = "INSERT INTO companies(companyName) VALUES (?)";
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getCompanyName());
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            user.setCompanyName(id);

            sqlStr = "INSERT INTO users(username, password, companies.companyName, userroles.userrole) VALUES (?, ?, ?, 'admin')";

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getCompanyName());
            ps.setString(4, user.getUserrole());
            ps.executeUpdate();

            return user;
        } catch (SQLException regErr) {
            System.out.println("Error in creating user");
            throw new ExamProjectException(regErr.getMessage());
        }
    }
}
