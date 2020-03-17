package sr.unasat.unabuku.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.unabuku.Entity.Order;
import sr.unasat.unabuku.Entity.User;

public class UnaBukuDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "unabuku.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE = "user";
    public static final String USER_USERID = "user_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_STUDNUMMER = "studnummer";

    private static final String BOOK_TABLE = "book";
    private static final String BOOK_BOOKID = "book_id";
    private static final String BOOK_TITLE = "title";
    private static final String BOOK_AUTHOR = "author";
    private static final String BOOK_SYNOPSIS = "synopsis";

    private static final String ORDER_TABLE = "orders";
    private static final String ORDER_ORDERID = "order_id";
    private static final String ORDER_USERID = "user_id";
    private static final String ORDER_BOOKID = "book_id";
    private static final String ORDER_ORDERDATE = "order_date";

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
                    "synopsis STRING NOT NULL)";

    public static final String SQL_CREATE_ORDERS_TABLE =
            "CREATE TABLE IF NOT EXISTS orders(" +
                    "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "book_id INTEGER NOT NULL," +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES user (user_id)," +
                    "FOREIGN KEY (book_id) REFERENCES book (book_id)" +
                    ")";

    public UnaBukuDAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        User user = findOneUserByUsername("viramdin");
        if (user != null) {
            return;
        }
        setDefaultUser();
    }

    private void setDefaultUser() {
        //Set default accounts
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, "viramdin");
        contentValues.put(USER_PASSWORD, "pass@123");
        contentValues.put(USER_EMAIL, "vi.ramdin@unasat.sr");
        contentValues.put(USER_STUDNUMMER, "SE/1118/017");
        insertOneUser(contentValues);
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

    public long insertOneUser(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(USER_TABLE, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public User findOneUserByUsername(String username) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s where username = '%s'", USER_TABLE, username);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(USER_USERID)),
                    cursor.getString(cursor.getColumnIndex(USER_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(USER_STUDNUMMER))
            );
        }
        db.close();
        return user;
    }

    public User authenticateUser(String username, String password) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s where username = '%s' AND password = '%s'", USER_TABLE, username, password);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(USER_USERID)),
                    cursor.getString(cursor.getColumnIndex(USER_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(USER_STUDNUMMER))
            );
        }
        db.close();
        return user;
    }

    public List<Order> getOrders() {
        List<Order> orderList = new ArrayList<Order>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s", BOOK_TABLE);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            orderList.add(
                    new Order(
                            cursor.getInt(cursor.getColumnIndex(ORDER_ORDERID)),
                            cursor.getInt(cursor.getColumnIndex(ORDER_USERID)),
                            cursor.getInt(cursor.getColumnIndex(ORDER_BOOKID)),
                            cursor.getString(cursor.getColumnIndex(ORDER_ORDERDATE))
                    )
            );
        }
        return orderList;
    }

    public long insertOneOrder(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(ORDER_TABLE, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public long updateOrder(ContentValues contentValues, int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowId = db.update(ORDER_TABLE, contentValues, ORDER_ORDERID + "=?", new String[]{Integer.toString(id)});
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public long deleteOrder(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowId = db.delete(ORDER_TABLE, ORDER_ORDERID + "=?", new String[]{Integer.toString(id)});
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }
}
