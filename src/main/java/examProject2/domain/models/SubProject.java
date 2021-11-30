package examProject2.domain.models;

import java.util.Date;

public class SubProject {
    private String subprojectName;
    private String subprojectOwner;
    private int subprojectID;
    private Date deadline;

    public SubProject(String subprojectName, String subprojectOwner, int subprojectID, Date deadline) {
        this.subprojectName = subprojectName;
        this.subprojectOwner = subprojectOwner;
        this.subprojectID = subprojectID;
        this.deadline = deadline;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    public String getSubprojectOwner() {
        return subprojectOwner;
    }

    public void setSubprojectOwner(String subprojectOwner) {
        this.subprojectOwner = subprojectOwner;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
