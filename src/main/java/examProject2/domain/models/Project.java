package examProject2.domain.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Project {
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */

    private String projectName;
    private String projectOwner;
    private int userID;
    private int projectID;

    public int getDays() {
        return days;
    }
    public String getTime() {
        return "days: " + days + " hours: " + hours;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    private int days;
    private int hours;
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

    public Project(String projectName, String projectOwner, int projectID, LocalDateTime deadline, int days, int hours) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectID = projectID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(this.deadline);
        this.days = days;
        this.hours = hours;
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
