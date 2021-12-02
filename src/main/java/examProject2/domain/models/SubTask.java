package examProject2.domain.models;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SubTask {
    private String subtaskName;
    private String subtaskOwner;
    private int subtaskID;
    private int userID;
    private int taskID;

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

    private int hours;
    private int minutes;
    private String deadlineFormatted;

    public SubTask(String subtaskName, int userID, int taskID, int hours, int minutes) {
        this.subtaskName = subtaskName;
        this.userID = userID;
        this.taskID = taskID;
        this.hours = hours;
        this.minutes = minutes;
        //this.deadlineFormatted = formatDate(deadline);
    }

    public SubTask(String subtaskName, String subtaskOwner, int subtaskID, int hours, int minutes) {
        this.subtaskName = subtaskName;
        this.subtaskOwner = subtaskOwner;
        this.subtaskID = subtaskID;
        this.hours = hours;
        this.minutes = minutes;
        //this.deadlineFormatted = formatDate(deadline);
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

    public String getTime() {
        return hours + ":" + minutes;
    }


    public String getDeadlineFormatted() {
        return deadlineFormatted;
    }
/*
    public String formatDate(LocalDateTime deadline){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = deadline.format(formatter);
        String date = str.substring(0,10).concat(" ");
        String time = str.substring(11);
        return date.concat(time);
    }
*/
    public int getUserID() {
        return userID;
    }

    public int getTaskID() {
        return taskID;
    }

}
