package examProject2.repositories;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
