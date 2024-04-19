package com.example.mayank.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mayank.Domain.UserModel;

import java.util.ArrayList;

public class UserdbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="user.db";
    private static final int DATABASE_VERSION =1;
    public UserdbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE usertb(id INTEGER PRIMARY KEY AUTOINCREMENT,Firstname TEXT,Lastname TEXT,email TEXT,password TEXT )";
        db.execSQL(CREATE_TABLE_QUERY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS usertb");
    }
    public boolean registeruser(String name1,String name2,String email1,String pass1)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Firstname",name1);
        contentValues.put("Lastname",name2);
        contentValues.put("email",email1);
        contentValues.put("password",pass1);

        long l =sqLiteDatabase.insert("usertb",null,contentValues);
        sqLiteDatabase.close();
        return l > 0;
    }
    boolean loggedin;
    public boolean userlogin(String email1,String pass1){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from usertb WHERE email ='"+email1+"'AND password='"+pass1+"'",null);
        if(cursor.moveToFirst())
        {
            loggedin=true;

        }
        else{
            loggedin = false;
        }
        return loggedin;
    }

    public boolean updateUser(String email1,String name1){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Firstname",name1);

        int i =sqLiteDatabase.update("usertb",values,"email=?",new String[]{email1});
        if(i>0){
            return true;

        }
        else {
            return false;
        }

    }

    public ArrayList<UserModel> getloggedinDetails(String email1){

        ArrayList<UserModel> al= new ArrayList<>();

        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * from usertb WHERE email ='"+email1+"'",null);
        if(cursor.moveToFirst()){
            String Firstname =cursor.getString(1);
            String email =cursor.getString(3);


            UserModel userModel = new UserModel();

            userModel.setEmail(email);
            userModel.setFirstname(Firstname);

            al.add(userModel);

        }
        return al;
    }

    public boolean deleteProfileHelper(String email1){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        int i = sqLiteDatabase.delete("usertb","email=?",new String[]{email1});
        if(i>0){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * from usertb  WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        // Check if any results are found
        boolean exists = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return whether the email exists or not
        return exists;
    }
}
