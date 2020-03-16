package sr.unasat.unabuku.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import sr.unasat.unabuku.Entity.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "unabuku.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS user(" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username STRING NOT NULL UNIQUE, " +
                    "password STRING NOT NULL, " +
                    "email STRING NOT NULL UNIQUE, " +
                    "studnummer STRING NOT NULL UNIQUE)";

    private static final String SQL_CREATE_BOOK_TABLE =
            "CREATE TABLE IF NOT EXISTS book(" +
                    "book_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title STRING NOT NULL," +
                    "author STRING NOT NULL," +
                    "synopsis STRING NOT NULL," +
                    "status STRING NOT NULL)";

    public static final String SQL_CREATE_ORDERS_TABLE =
            "CREATE TABLE IF NOT EXISTS orders(" +
                    "order_nr INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "book_id INTEGER NOT NULL," +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES user (user_id)," +
                    "FOREIGN KEY (book_id) REFERENCES book (book_id)" +
                    ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setDefaultCredentials();
        getWritableDatabase().execSQL(SQL_CREATE_USER_TABLE);
    }

    private void setDefaultCredentials() {

        //Set default username and password
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "viramdin");
        contentValues.put("password", "pass@123");
        contentValues.put("email", "vi.ramdin@unasat.sr");
        contentValues.put("studnummer", "SE/1118/017");
        insertOneRecord("user", contentValues);
    }

    public void createUser(ContentValues contentValues) {
        getWritableDatabase().insert("user", " ", contentValues);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_BOOK_TABLE);
        db.execSQL(SQL_CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertOneRecord(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(tableName, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public User authenticateUser(String username, String password) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        db.close();
        return user;
    }
}
