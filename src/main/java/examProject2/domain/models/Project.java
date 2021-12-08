package examProject2.domain.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Project {

    private String projectName;
    private String projectOwner;
    private int userID;
    private int projectID;
    private LocalDateTime deadline;
    private String deadlineFormatted;

    public Project() {
    }

    public Project(String projectName, int userID, LocalDateTime deadline) {
        this.projectName = projectName;
        this.userID = userID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
    }

    public Project(String projectName, String projectOwner, int projectID) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectID = projectID;
    }

    public Project(String projectName, String projectOwner, int projectID, LocalDateTime deadline) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectID = projectID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(this.deadline);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getDeadlineFormatted() {
        return deadlineFormatted;
    }

    public void setDeadlineFormatted(String deadlineFormatted) {
        this.deadlineFormatted = deadlineFormatted;
    }

    public String formatDate(LocalDateTime deadline){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = deadline.format(formatter);
        String date = str.substring(0,10).concat(" ");
        String time = str.substring(11);
        return date.concat(time);
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                '}';
    }
}
