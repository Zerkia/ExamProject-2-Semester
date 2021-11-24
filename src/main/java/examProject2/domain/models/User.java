package examProject2.domain.models;

public class User {

    private String username;
    private String password;
    private int userroleID;
    private int userID;

    public User(){
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int userroleID) {
        this.username = username;
        this.password = password;
        this.userroleID = userroleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserroleID() {
        return userroleID;
    }

    public void setUserroleID(int userroleID) {
        this.userroleID = userroleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

