package sr.unasat.unabuku;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin) {
        editor.putBoolean("loggedInmode", logggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }

    public void setUserId(int userId) {
        editor.putInt("userId", userId);
        editor.commit();
    }

    public int getUserId() {
        return prefs.getInt("userId", 0);
    }

    public void setUserName(String userName) {
        editor.putString("userName", userName);
        editor.commit();
    }

    public String getUserName() {
        return prefs.getString("userName", null);
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        return prefs.getString("email", null);
    }

    public void setStudNummer(String studNummer) {
        editor.putString("studNummer", studNummer);
        editor.commit();
    }

    public String getStudNummer() {
        return prefs.getString("studNummer", null);
    }
}
