package com.example.mayank.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SellerdbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="seller.db";
    private static final int DATABASE_VERSION =1;
    public SellerdbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE sellertb(id INTEGER PRIMARY KEY AUTOINCREMENT,Firstname TEXT,Lastname TEXT,email TEXT,password TEXT )";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS sellertb");

    }
    public boolean registersell(String name1,String name2,String email1,String pass1)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Firstname",name1);
        contentValues.put("email",email1);
        contentValues.put("password",pass1);



        long l =sqLiteDatabase.insert("sellertb",null,contentValues);
        sqLiteDatabase.close();
        return l > 0;


    }
    boolean loggedin;
    public boolean selllogin(String email1,String pass1){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from sellertb WHERE email ='"+email1+"'AND password='"+pass1+"'",null);
        if(cursor.moveToFirst())
        {
            loggedin=true;

        }
        else{
            loggedin = false;
        }
        return loggedin;
    }


    public ArrayList getallDetails(){
        ArrayList alusers = new ArrayList();
        ArrayList al = new ArrayList();
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * from sellertb ",null);
        if(cursor.moveToFirst()){

            do {
                String name =cursor.getString(1);
                String email =cursor.getString(2);


                al.add(name);
                al.add(email);


                alusers.add(al);

            }while(cursor.moveToNext());
        }
        return al;


    }
    public boolean updateUser(String email1,String name1,String gender1){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name1);

        int i =sqLiteDatabase.update("sellertb",values,"email=?",new String[]{email1});
        if(i>0){
            return true;

        }
        else {
            return false;
        }

    }



    /*public ArrayList<UserModel> getloggedinDetails(String email1){

        ArrayList<UserModel> al= new ArrayList<>();

        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * from register WHERE email ='"+email1+"'",null);
        if(cursor.moveToFirst()){
            String name =cursor.getString(1);
            String email =cursor.getString(2);
            String gender =cursor.getString(4);

            UserModel userModel= new UserModel();

            userModel.setEmail(email);
            userModel.setName(name);
            userModel.setGender(gender);

            al.add(userModel);

        }
        return al;
    }*/

    public boolean deleteProfileHelper(String email1){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        int i = sqLiteDatabase.delete("sellertb","email=?",new String[]{email1});
        if(i>0){
            return true;
        }
        else{
            return false;
        }

    }
}
