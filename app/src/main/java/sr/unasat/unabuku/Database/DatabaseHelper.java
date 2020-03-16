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
            "CREATE TABLE user(" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username STRING NOT NULL UNIQUE, " +
                    "email STRING NOT NULL UNIQUE, " +
                    "password STRING NOT NULL, " +
                    "studnummer STRING NOT NULL UNIQUE," +
                    "logged_in INTEGER NOT NULL)";

    private static final String SQL_CREATE_BOOK_TABLE =
            "CREATE TABLE book(" +
                    "book_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title STRING NOT NULL," +
                    "author STRING NOT NULL," +
                    "synopsis STRING NOT NULL," +
                    "status STRING NOT NULL)";

    public static final String SQL_CREATE_ORDERS_TABLE = "CREATE TABLE orders(" +
            "order_nr INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "book_id INTEGER NOT NULL," +
            "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
            "FOREIGN KEY (user_id) REFERENCES user (user_id)," +
            "FOREIGN KEY (book_id) REFERENCES book (book_id)" +
            ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase().execSQL(SQL_CREATE_USER_TABLE);
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

//    public User loginUser(String username, String password) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select count(*) from user where username=? and password=?", new String[]{username, password});
//        if (cursor.getCount() == 0) {
//            return null;
//        } else {
//            cursor.moveToFirst();
//
//            ContentValues updateValues = new ContentValues();
//            updateValues.put("logged_in", 1);
//            int userId = cursor.getInt(0);
//            updateUser(updateValues, userId);
//            return getUser(userId);
//        }
//    }

//    private boolean updateUser(ContentValues contentValues, int id) {
//        SQLiteDatabase db = getWritableDatabase();
//
//        int updateQuery = db.update("user", contentValues, "id= ?", new String[]{String.valueOf(id)});
//
//        return updateQuery > 0;
//    }
//
//    public User getUser(int id) {
//        SQLiteDatabase db = getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT users.id,username,role,name FROM user WHERE users.student_id = students.id AND users.id = ?", new String[]{String.valueOf(id)});
//
//        if (cursor.getCount() == 0) {
//            return null;
//        } else {
//            cursor.moveToFirst();
//            return new User(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3)
//
//            );
//        }
//    }
}
