package examProject2.domain.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SubTask {
    private String subtaskName;
    private String subtaskOwner;
    private int subtaskID;
    private LocalDateTime deadline;
    private String deadlineFormatted;

    public SubTask(String subtaskName, String subtaskOwner, int subtaskID, LocalDateTime deadline) {
        this.subtaskName = subtaskName;
        this.subtaskOwner = subtaskOwner;
        this.subtaskID = subtaskID;
        this.deadline = deadline;
        this.deadlineFormatted = formatDate(deadline);
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
}
