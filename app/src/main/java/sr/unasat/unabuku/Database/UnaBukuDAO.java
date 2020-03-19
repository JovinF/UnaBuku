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
    public static final String USER_NAME = "name";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_STUDNUMMER = "studnummer";

    private static final String ORDER_TABLE = "orders";
    private static final String ORDER_ORDERID = "order_id";
    private static final String ORDER_USERID = "user_id";
    private static final String ORDER_BOOKTITLE = "book_title";
    private static final String ORDER_BOOKAUTHOR = "book_author";
    private static final String ORDER_BOOKSYNOPSIS = "book_synopsis";
    private static final String ORDER_AMOUNT = "amount";
    private static final String ORDER_ORDERDATE = "order_date";

    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS user(" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name STRING NOT NULL, " +
                    "username STRING NOT NULL UNIQUE, " +
                    "password STRING NOT NULL, " +
                    "email STRING NOT NULL UNIQUE, " +
                    "studnummer STRING NOT NULL UNIQUE)";

    public static final String SQL_CREATE_ORDERS_TABLE =
            "CREATE TABLE IF NOT EXISTS orders(" +
                    "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "book_title STRING NOT NULL," +
                    "book_author STRING NOT NULL," +
                    "book_synopsis STRING," +
                    "amount INETEGER NOT NULL," +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES user (user_id)" +
                    ")";

    public UnaBukuDAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setDefaultUser();
//        setDummyOrders();
    }

    private void setDefaultUser() {
        //Set default accounts
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, "Viresh Ramdin");
        contentValues.put(USER_USERNAME, "viramdin");
        contentValues.put(USER_PASSWORD, "pass@123");
        contentValues.put(USER_EMAIL, "vi.ramdin@unasat.sr");
        contentValues.put(USER_STUDNUMMER, "SE/1118/017");
        insertOneUser(contentValues);

        contentValues.clear();

        contentValues.put(USER_NAME, "Jovin Fransman");
        contentValues.put(USER_USERNAME, "jfransman");
        contentValues.put(USER_PASSWORD, "pass@123");
        contentValues.put(USER_EMAIL, "j.fransman@unasat.sr");
        contentValues.put(USER_STUDNUMMER, "SE/1118/006");
        insertOneUser(contentValues);

        contentValues.clear();

        contentValues.put(USER_NAME, "Farah Troenodrono");
        contentValues.put(USER_USERNAME, "ftroenodrono");
        contentValues.put(USER_PASSWORD, "pass@123");
        contentValues.put(USER_EMAIL, "f.troenodrono@unasat.sr");
        contentValues.put(USER_STUDNUMMER, "SE/1118/022");
        insertOneUser(contentValues);
    }

    private void setDummyOrders() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_USERID, 1);
        contentValues.put(ORDER_BOOKTITLE, "Boek 1");
        contentValues.put(ORDER_BOOKAUTHOR, "Viresh 1");
        contentValues.put(ORDER_AMOUNT, 2);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 1);
        contentValues.put(ORDER_BOOKTITLE, "Boek 2");
        contentValues.put(ORDER_BOOKAUTHOR, "Viresh 2");
        contentValues.put(ORDER_AMOUNT, 1);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 1);
        contentValues.put(ORDER_BOOKTITLE, "Boek 3");
        contentValues.put(ORDER_BOOKAUTHOR, "Viresh 3");
        contentValues.put(ORDER_AMOUNT, 3);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.clear();

        contentValues.put(ORDER_USERID, 2);
        contentValues.put(ORDER_BOOKTITLE, "Boek 1");
        contentValues.put(ORDER_BOOKAUTHOR, "Jovin 1");
        contentValues.put(ORDER_AMOUNT, 5);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 2);
        contentValues.put(ORDER_BOOKTITLE, "Boek 2");
        contentValues.put(ORDER_BOOKAUTHOR, "Jovin 2");
        contentValues.put(ORDER_AMOUNT, 1);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 2);
        contentValues.put(ORDER_BOOKTITLE, "Boek 3");
        contentValues.put(ORDER_BOOKAUTHOR, "Jovin 3");
        contentValues.put(ORDER_AMOUNT, 7);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 3);
        contentValues.put(ORDER_BOOKTITLE, "Boek 1");
        contentValues.put(ORDER_BOOKAUTHOR, "Farah 1");
        contentValues.put(ORDER_AMOUNT, 5);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 3);
        contentValues.put(ORDER_BOOKTITLE, "Boek 2");
        contentValues.put(ORDER_BOOKAUTHOR, "Farah 2");
        contentValues.put(ORDER_AMOUNT, 1);
        insertOneOrder(contentValues);

        contentValues.clear();

        contentValues.put(ORDER_USERID, 3);
        contentValues.put(ORDER_BOOKTITLE, "Boek 3");
        contentValues.put(ORDER_BOOKAUTHOR, "Farah 3");
        contentValues.put(ORDER_AMOUNT, 9);
        insertOneOrder(contentValues);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
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

//    public User findOneUserByUsername(String username) {
//        User user = null;
//        SQLiteDatabase db = getReadableDatabase();
//        String sql = String.format("select * from %s where username = '%s'", USER_TABLE, username);
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor.moveToFirst()) {
//            user = new User(
//                    cursor.getInt(cursor.getColumnIndex(USER_USERID)),
//                    cursor.getString(cursor.getColumnIndex(USER_NAME)),
//                    cursor.getString(cursor.getColumnIndex(USER_USERNAME)),
//                    cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
//                    cursor.getString(cursor.getColumnIndex(USER_STUDNUMMER))
//            );
//        }
//        db.close();
//        return user;
//    }

    public User authenticateUser(String username, String password) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s where username = '%s' AND password = '%s'", USER_TABLE, username, password);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(USER_USERID)),
                    cursor.getString(cursor.getColumnIndex(USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(USER_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(USER_STUDNUMMER))
            );
        }
        db.close();
        return user;
    }

    public List<Order> getOrdersByUserId(int id) {
        List<Order> orderList = new ArrayList<Order>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s where user_id='%s'", ORDER_TABLE, id);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            orderList.add(
                    new Order(
                            cursor.getInt(cursor.getColumnIndex(ORDER_ORDERID)),
                            cursor.getInt(cursor.getColumnIndex(ORDER_USERID)),
                            cursor.getString(cursor.getColumnIndex(ORDER_BOOKTITLE)),
                            cursor.getString(cursor.getColumnIndex(ORDER_BOOKAUTHOR)),
                            cursor.getString(cursor.getColumnIndex(ORDER_BOOKSYNOPSIS)),
                            cursor.getInt(cursor.getColumnIndex(ORDER_AMOUNT)),
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
