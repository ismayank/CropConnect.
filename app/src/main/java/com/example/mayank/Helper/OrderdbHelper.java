package com.example.mayank.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.example.mayank.Domain.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderdbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "order.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ordertb";

    public OrderdbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "email TEXT, "
                + "address TEXT, "
                + "phone TEXT, "
                + "amount REAL, "
                + "status TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addOrder(String email, String address, String phone, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("address", address);
        values.put("phone", phone);
        values.put("amount", amount);
        values.put("status", "Pending");
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<String> getOrderDetails(String email) {
        ArrayList<String> orderDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"address", "phone", "amount", "status"};
        String selection = "email=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            orderDetails.add(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            orderDetails.add(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            orderDetails.add(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))));
            orderDetails.add(cursor.getString(cursor.getColumnIndexOrThrow("status")));
        }
        cursor.close();
        db.close();
        return orderDetails;
    }

    public List<Order> getOrdersByEmail(String email) {
        // Initialize an empty list to hold orders
        List<Order> orders = new ArrayList<>();

        // Check for null email parameter
        if (email == null) {
            // Log an error or handle the null case
            // For example, return an empty list
            return orders;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "ordertb",
                    new String[]{"email", "address", "phone", "amount", "status"},
                    "email = ?",
                    new String[]{email},
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String orderEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                    double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                    // Create an Order object and add it to the list
                    Order order = new Order(orderEmail, address, phone, amount);
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            // Log or handle the exception as needed
            e.printStackTrace();
        } finally {
            // Always close the cursor and database
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // Return the list of orders
        return orders;
    }


    public boolean updateOrderStatus(String email, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        String whereClause = "email=?";
        String[] whereArgs = {email};
        int rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return rowsUpdated > 0;
    }

    public boolean deleteOrder(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "email=?";
        String[] whereArgs = {email};
        int rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rowsDeleted > 0;
    }
}
