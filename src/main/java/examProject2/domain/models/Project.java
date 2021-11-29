package examProject2.domain.models;

import java.util.Date;

public class Project {

    private String projectName;
    private String projectOwner;
    private int userID;
    private int projectID;
    private Date deadline;

    public Project(String projectName, int userID) {
        this.projectName = projectName;
        this.userID = userID;
    }

    public Project(String projectName, String projectOwner, int projectID) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectID = projectID;
    }

    public Project(String projectName, String projectOwner, int projectID, Date deadline) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectID = projectID;
        this.deadline = deadline;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                '}';
    }
}
