package examProject2.domain.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Task {
    private String taskName;
    private String taskOwner;
    private int taskID;
    private int userID;

    public int getSubprojectID() {
        return subprojectID;
    }

    private int subprojectID;
    private LocalDateTime deadline;
    private String deadlineFormatted;

    public Task(String taskName, int userID, int subprojectID, LocalDateTime deadline) {
        this.taskName = taskName;
        this.userID = userID;
        this.subprojectID = subprojectID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
    }

    public Task(String taskName, String taskOwner, int taskID, LocalDateTime deadline) {
        this.taskName = taskName;
        this.taskOwner = taskOwner;
        this.taskID = taskID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
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

    public String formatDate(LocalDateTime deadline){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = deadline.format(formatter);
        String date = str.substring(0,10).concat(" ");
        String time = str.substring(11);
        return date.concat(time);
    }

    public int getUserID() {
        return userID;
    }
}
