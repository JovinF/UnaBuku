package sr.unasat.unabuku.Entity;

public class User {
    private int userId;
    private String userName;
    private String password;
    private String studNummer;

    public User(int userId, String userName, String password, String studNummer) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.studNummer = studNummer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudNummer() {
        return studNummer;
    }

    public void setStudNummer(String studNummer) {
        this.studNummer = studNummer;
    }
}
