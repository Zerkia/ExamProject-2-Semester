package examProject2.domain.models;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SubTask {
    /**
     * @author Nikolaj Pregaard
     * @author Mads Haderup
     */
    private String subtaskName;
    private String subtaskOwner;
    private int subtaskID;
    private int userID;
    private int taskID;
    private int hours;
    private int minutes;

    public SubTask(String subtaskName, int userID, int taskID, int hours, int minutes) {
        this.subtaskName = subtaskName;
        this.userID = userID;
        this.taskID = taskID;
        this.hours = hours;
        this.minutes = minutes;
    }

    public SubTask(String subtaskName, String subtaskOwner, int subtaskID, int hours, int minutes) {
        this.subtaskName = subtaskName;
        this.subtaskOwner = subtaskOwner;
        this.subtaskID = subtaskID;
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getSubtaskName() {
        return subtaskName;
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public String getSubtaskOwner() {
        return subtaskOwner;
    }

    public void setSubtaskOwner(String subtaskOwner) {
        this.subtaskOwner = subtaskOwner;
    }

    public int getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(int subtaskID) {
        this.subtaskID = subtaskID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * @author Mads Haderup
     */
    public String getTime() {
        return hours + ":" + minutes;
    }
}
