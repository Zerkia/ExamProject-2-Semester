package examProject2.domain.models;

public class SubProject {
    private String subProjectName;
    private String projectOwner;
    private int userID;
    private int subProjectID;
    private int projectID;

    public SubProject(String subProjectName, int projectID){
        this.subProjectName = subProjectName;
        this.projectID = projectID;

    }
    public SubProject(String subProjectName, int projectID, int subProjectID){
        this.subProjectName = subProjectName;
        this.projectID = projectID;
        this.subProjectID = subProjectID;

    }
}
