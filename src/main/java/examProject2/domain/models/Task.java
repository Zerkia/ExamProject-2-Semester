package examProject2.domain.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Task {
    private String taskName;
    private String taskOwner;
    private int taskID;
    private int userID;
    private int subprojectID;
    private LocalDateTime deadline;
    private String deadlineFormatted;
    private int days;
    private int hours;

    public Task(String taskName, int userID, int subprojectID, LocalDateTime deadline) {
        this.taskName = taskName;
        this.userID = userID;
        this.subprojectID = subprojectID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
    }

    public Task(String taskName, String taskOwner, int taskID, LocalDateTime deadline, int days, int hours) {
        this.taskName = taskName;
        this.taskOwner = taskOwner;
        this.taskID = taskID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
        this.days = days;
        this.hours = hours;
    }

    public int getSubtasksTime(List<SubTask> subTasks){
        int hours = 0;
        int minutes = 0;
        for(SubTask subTask : subTasks){
            hours = hours + subTask.getHours();
            minutes = minutes + subTask.getMinutes();
        }
        int extraHours = (int)(((double)minutes) / 60.0);
        return hours + extraHours;

    }

    public String getTime() {
        return "days: " + days + " hours: " + hours;
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
    public int getSubprojectID() {
        return subprojectID;
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

    public void setTime(int hours, List<SubTask> subTasks) {
        this.hours = hours;
    }
}
