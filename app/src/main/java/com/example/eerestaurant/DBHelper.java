package com.example.eerestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //User Table (register and login table)
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeruser";
    public static final String COL_ID = "ID";
    public static final String COL_Username = "username";
    public static final String COL_FULL_NAME = "fullname";
    public static final String COL_Password = "password";
    public static final String COL_PHOTO = "photo";
    public static final String COL_Role = "role";

    //Booking Table (Booking information containing the same username).
    public static final String BOOKING_TABLE="bookingtable";
    public static final String BOOKING_ID = "bookingID";
    public static final String BOOKING_TABLE_ID = "tableId";
    public static final String BOOKING_USERNAME = "bookingusername";
    public static final String BOOKING_NAME= "bookingName";
    public static final String NUMBER_OF_PEOPLE= "numberofpeople";
    public static final String PHONE_NUMBER="phonenumber";
    public static final String DATE = "date";
    public static final String TIME = "time";



    //Starting the database from fresh if the version number is changed in ascending order.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 15);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Making the Tables.

        sqLiteDatabase.execSQL("CREATE TABLE bookingtable (bookingID INTEGER PRIMARY KEY AUTOINCREMENT, tableID TEXT, bookingusername TEXT, name TEXT, numberofpeople TEXT, phonenumber TEXT, date TEXT, time TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, fullname TEXT, password TEXT, roles TEXT, photo BLOB)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Dropping the table if it already exists and create a new one.
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + BOOKING_TABLE);
        onCreate(sqLiteDatabase);
    }


    //Function to add the user that is registering to the table registeruser
    public long addUser (String user, String password, String fullname, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("fullname", fullname);
        contentValues.put("password", password);
        contentValues.put("photo", image);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    //Admin page would have used this to see all the users registered on the app
    public Cursor userdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registeruser", null);
        return cursor;
    }

    //Admin page would have used this to see all the bookings made by users on the app.
    public Cursor bookingdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bookingtable", null);
        return cursor;
    }

    //This is to get a specific amount of data from the table of registered users based off of the username
    public Cursor specUserData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registeruser where username = '" + username + "'", null);
        return cursor;
    }

    //This is to get a specific amount of data from the table of bookings based off of the username
    public Cursor specBookingData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bookingtable where bookingusername = '" + username + "'", null);
        return cursor;
    }

    //Method to add bookings to the bookings table.
    public long addBooking (String tableid, String username, String bookingName, String numberofpeople, String phoneNumber, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tableID", tableid);
        contentValues.put("bookingusername", username);
        contentValues.put("name", bookingName);
        contentValues.put("numberofpeople", numberofpeople);
        contentValues.put("phonenumber", phoneNumber);
        contentValues.put("date", date);
        contentValues.put("time", time);
        long res = db.insert("bookingtable", null, contentValues);
        db.close();
        return res;
    }


    //Checking if user already exists within the registeruser table, if exists, the user will be logged in.
    public boolean checkUser(String username, String password){
        String[] columns = { COL_ID };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_Username + "=?" + " and " + COL_Password + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0){
            return true;
        }
        else{
            return false;
        }
    }

    //Checking if the username entered by the user registering already exists in the table, if it exists count will be higher than 0. The boolean variable can then be used in an if statement later
    //on.
    public boolean checkUsername(String username) {
        String[] columns = {COL_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_Username + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
