package examProject2.domain.models;

import java.util.Date;

public class Task {
    private String taskName;
    private String taskOwner;
    private int taskID;
    private Date deadline;

    public Task(String taskName, int subprojectID) {
        this.taskName = taskName;
        this.taskID = subprojectID;
    }

    public Task(String taskName, String taskOwner, int taskID, Date deadline) {
        this.taskName = taskName;
        this.taskOwner = taskOwner;
        this.taskID = taskID;
        this.deadline = deadline;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
