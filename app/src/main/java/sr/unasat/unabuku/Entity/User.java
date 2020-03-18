package sr.unasat.unabuku.Entity;

public class User {
    private int userId;
    private String name;
    private String userName;
    private String email;
    private String studNummer;


    public User(int userId, String name, String userName, String email, String studNummer) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.studNummer = studNummer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudNummer() {
        return studNummer;
    }

    public void setStudNummer(String studNummer) {
        this.studNummer = studNummer;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", studNummer='" + studNummer + '\'' +
                '}';
    }
}
