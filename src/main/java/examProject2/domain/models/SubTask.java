package examProject2.domain.models;

import java.util.Date;

public class SubTask {
    private String subtaskName;
    private String subtaskOwner;
    private int subtaskID;
    private Date deadline;

    public SubTask(String subtaskName, String subtaskOwner, int subtaskID, Date deadline) {
        this.subtaskName = subtaskName;
        this.subtaskOwner = subtaskOwner;
        this.subtaskID = subtaskID;
        this.deadline = deadline;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
