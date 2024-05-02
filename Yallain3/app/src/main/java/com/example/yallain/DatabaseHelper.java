package com.example.yallain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SignLog.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (email TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE rents (sport TEXT , stadium_name TEXT, rent_date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS rents");
        onCreate(db);
    }

    public boolean insertData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = db.insert("users", null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean insertRent(String sport,String stadiumName, String rentDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sport", sport);
        contentValues.put("stadium_name", stadiumName);
        contentValues.put("rent_date", rentDate);
        long result = db.insert("rents", null, contentValues);
        return result != -1;
    }




    public ArrayList<String> getRentalHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> historyList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM rents", null);
        if (cursor.moveToFirst()) {
            int sportIndex = cursor.getColumnIndex("sport");
            int stadiumNameIndex = cursor.getColumnIndex("stadium_name");
            int rentDateIndex = cursor.getColumnIndex("rent_date");

            do {
                if (sportIndex != -1 && stadiumNameIndex != -1 && rentDateIndex != -1) {
                    String sport = cursor.getString(sportIndex);
                    String stadiumName = cursor.getString(stadiumNameIndex);
                    String rentDate = cursor.getString(rentDateIndex);
                    String historyItem = "Sport: " + sport + ", Stadium: " + stadiumName + ", Date: " + rentDate;
                    historyList.add(historyItem);

                    Log.d("DatabaseHelper", "getRentalHistory: " + historyItem);
                } else {
                    Log.e("DatabaseHelper", "Column index not found");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return historyList;
    }
    public String getUsername(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("email");
            if (usernameIndex != -1) {
                username = cursor.getString(usernameIndex);
                Log.d("DatabaseHelper", "getUsername: Found username in database: " + username);
            }
            else {
                Log.e("DatabaseHelper", "getUsername: Username column not found");
            }
        } else {
            Log.e("DatabaseHelper", "getUsername: No data found for email: " + email);

        }

        cursor.close();
        return username;
    }


}
