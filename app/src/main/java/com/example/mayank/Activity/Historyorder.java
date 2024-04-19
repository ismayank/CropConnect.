package com.example.mayank.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayank.Domain.Order;
import com.example.mayank.Helper.OrderdbHelper;

import com.example.mayank.Helper.SessionManager;
import com.example.mayank.R;

import java.util.List;

public class Historyorder extends BaseActivity {

    private OrderdbHelper orderdbHelper;
    SessionManager sessionManager;

    private LinearLayout orderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyorder);
        sessionManager = new SessionManager(getApplicationContext());

        String email = sessionManager.getSessionDetails("key_session_email");


        // Initialize LinearLayout
        orderLayout = findViewById(R.id.orderLayout);

        // Initialize OrderdbHelper
        orderdbHelper = new OrderdbHelper(this);

        // Get the email from the intent or other source (make sure to provide the email)

        // Retrieve orders from the database for the given email
        List<Order> orders = orderdbHelper.getOrdersByEmail(email);

        // Display orders in the LinearLayout
        displayOrderHistory(orders);
    }
    public void backorder(View view){
        finish();
    }

    private void displayOrderHistory(List<Order> orders) {
        // Clear existing views if any
        orderLayout.removeAllViews();

        // Iterate over each order and add a view for each order
        for (Order order : orders) {
            // Create a new TextView for each order and set its text
            TextView orderView = new TextView(this);
            orderView.setText("Email: " + order.getEmail() + "\n" +
                    "Address: " + order.getAddress() + "\n" +
                    "Phone: " + order.getPhone() + "\n" +
                    "Amount: " + order.getAmount() + "\n");
            orderView.setPadding(16, 8, 16, 8);
            Typeface boldTypeface = Typeface.create("monospace", Typeface.BOLD);

// Apply the bold typeface to the TextView
            orderView.setTypeface(boldTypeface);
            orderView.setTextSize(18);


            // Add the TextView to the LinearLayout
            orderLayout.addView(orderView);

            // Add a separator line between orders
            View separator = new View(this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            separator.setBackgroundColor(getResources().getColor(R.color.black));
            orderLayout.addView(separator);
        }
    }
}
