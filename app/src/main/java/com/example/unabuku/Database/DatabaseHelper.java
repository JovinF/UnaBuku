package com.example.unabuku.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name =  "dbandroid.db";
    static int version = 1;
String createTableUser = "create table if not exists user(id integer primary key autoincrement, user_name text, user_email text, user_password text, user_studnummer text)";
    public DatabaseHelper(@Nullable Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createTableUser);
    }

    public void createUser(ContentValues contentValues){
        getWritableDatabase().insert("user"," ",contentValues);
    }

    public boolean isLoginValid(String username, String password){
        String sql = "Select count(*) from user where user_name='"+username+"' and user_password='"+password+"'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();

        if (l==1){
            return true;

        } else{
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
