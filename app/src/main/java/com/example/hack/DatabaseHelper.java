package com.example.hack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="database1.db";
    public static final String TABLE_NAME="table1";

    public static final String COL1="Username";
    public static final String COL2="FullName";
    public static final String COL3="Email";
    public static final String COL4="Password";
    public static final String COL5="Age";
    public static final String COL6="Role";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " +  TABLE_NAME + " (Username TEXT PRIMARY KEY, " +
                " FullName TEXT, Email TEXT, Password TEXT, Age TEXT, Role TEXT)";

        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String Username, String FullName, String Email, String Password, String Age, String Role )
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1,Username);
        contentValues.put(COL2,FullName);
        contentValues.put(COL3,Email);
        contentValues.put(COL4,Password);
        contentValues.put(COL5,Age);
        contentValues.put(COL6,Role);

        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor showData(String name ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=\"" + name + "\";",null);
        return data;
    }

}
