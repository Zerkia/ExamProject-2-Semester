package examProject2.domain.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SubProject {
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */
    private String subprojectName;
    private String subprojectOwner;
    private int subprojectID;
    private int userID;
    private int projectID;


    public String getTime() {
        return "days: " + days + " hours: " + hours;
    }
    public int getDays() {
        return days;
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

    public SubProject(String subprojectName, int userID, int projectID, LocalDateTime deadline) {
        this.subprojectName = subprojectName;
        this.userID = userID;
        this.projectID = projectID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
    }

    public SubProject(String subprojectName, String subprojectOwner, int subprojectID, LocalDateTime deadline, int days, int hours) {
        this.subprojectName = subprojectName;
        this.subprojectOwner = subprojectOwner;
        this.subprojectID = subprojectID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
        this.days = days;
        this.hours = hours;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public String getSubprojectOwner() {
        return subprojectOwner;
    }

    public void setSubprojectOwner(String subprojectOwner) {
        this.subprojectOwner = subprojectOwner;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
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
    /**
     * @author Mads Haderup
     */
    public String formatDate(LocalDateTime deadline){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = deadline.format(formatter);
        String date = str.substring(0,10).concat(" ");
        String time = str.substring(11);
        return date.concat(time);
    }
}
