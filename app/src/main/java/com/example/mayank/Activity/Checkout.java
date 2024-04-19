package com.example.mayank.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayank.Helper.OrderdbHelper;
import com.example.mayank.Helper.SessionManager;
import com.example.mayank.R;

public class Checkout extends BaseActivity {

    private EditText deliveryAddress, contactNumber, name1;
    private TextView optionCreditCard, optionDebitCard, optionCashOnDelivery, amount,email1;
    private Button payNowButton;
    private String amount1;
    SessionManager sessionManager;
    private String selectedPaymentOption = ""; // Store the selected payment option
    private OrderdbHelper orderdbHelper; // Database helper for order management

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        sessionManager = new SessionManager(getApplicationContext());

        // Initialize UI elements
        deliveryAddress = findViewById(R.id.deliveryAddress);
        email1 = findViewById(R.id.email1);
        name1 = findViewById(R.id.name1);
        contactNumber = findViewById(R.id.contactNumber);
        optionCreditCard = findViewById(R.id.optionCreditCard);
        optionDebitCard = findViewById(R.id.optionDebitCard);
        optionCashOnDelivery = findViewById(R.id.optionCashOnDelivery);
        payNowButton = findViewById(R.id.payNowButton);
        amount = findViewById(R.id.amount);

        String name = sessionManager.getSessionDetails("key_session_email");
        email1.setText(name);

        amount1 = String.valueOf(getIntent().getDoubleExtra("key_pay", 0.0));
        amount.setText("$" + amount1);

        // Initialize OrderdbHelper
        orderdbHelper = new OrderdbHelper(this);

        // Set onClick listeners for each payment option
        setPaymentOptionOnClickListener(optionCreditCard, "Credit Card");
        setPaymentOptionOnClickListener(optionDebitCard, "Debit Card");
        setPaymentOptionOnClickListener(optionCashOnDelivery, "Cash on Delivery");

        // Handle "Pay Now" button click event
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get delivery address and selected payment option
                String address = deliveryAddress.getText().toString();
                String phone = contactNumber.getText().toString();
                String name = name1.getText().toString();
                String email = email1.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Checkout.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(Checkout.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(Checkout.this, "Please enter your address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedPaymentOption.isEmpty()) {
                    Toast.makeText(Checkout.this, "Please select a payment option.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle payment logic
                handlePayment(email,address, phone, selectedPaymentOption);
            }
        });
    }

    private void setPaymentOptionOnClickListener(TextView paymentOptionView, String paymentOption) {
        paymentOptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the text colors of all payment options to black
                optionCreditCard.setTextColor(Color.BLACK);
                optionDebitCard.setTextColor(Color.BLACK);
                optionCashOnDelivery.setTextColor(Color.BLACK);

                // Use custom color for the selected payment option
                String customColorHex = "#169955";
                int customColor = Color.parseColor(customColorHex);

                // Highlight the selected payment option by changing text color
                paymentOptionView.setTextColor(customColor);
                selectedPaymentOption = paymentOption;
            }
        });
    }
    private void handlePayment(String email1,String address, String phone, String paymentOption) {
        // Add order details to the order database
        boolean isOrderAdded = orderdbHelper.addOrder(email1, address, phone, Double.parseDouble(amount1));
        if (isOrderAdded) {
            // Navigate to the OrderSuccess activity
            Intent intent = new Intent(getApplicationContext(),OrderSuccess.class);
            intent.putExtra("key_email",email1);
            Toast.makeText(this, "Payment processing for address: " + address + ", phone: " + phone + " with payment option: " + paymentOption, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to process payment.", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to validate phone numbers
    private boolean isValidPhoneNumber(String phone) {
        // Use a regex to validate phone numbers
        String phoneRegex = "^[0-9]{10}$"; // Example regex for a 10-digit phone number
        return phone.matches(phoneRegex);
    }
    public void back(View view){
        finish();
    }
}
