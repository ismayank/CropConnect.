package com.example.mayank.Activity;

        import androidx.appcompat.app.AppCompatActivity;
        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.os.Bundle;
        import com.example.mayank.R;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

public class SellerCheckout extends BaseActivity {

    private EditText deliveryAddress;
    TextView optionCreditCard, optionDebitCard, optionCashOnDelivery,amount;
    private Button payNowButton;
    String amount1;

    private String selectedPaymentOption = ""; // Store the selected payment option

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_checkout);

        // Initialize UI elements
        deliveryAddress = findViewById(R.id.deliveryAddress);
        optionCreditCard = findViewById(R.id.optionCreditCard);
        optionDebitCard = findViewById(R.id.optionDebitCard);
        optionCashOnDelivery = findViewById(R.id.optionCashOnDelivery);
        payNowButton = findViewById(R.id.payNowButton);
        amount = (TextView) findViewById(R.id.amount);

        amount1 = String.valueOf(getIntent().getDoubleExtra("key_pay", 0.0));


        amount.setText("$"+amount1);

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
                if (selectedPaymentOption.isEmpty()) {
                    Toast.makeText(SellerCheckout.this, "Please select a Recieving option.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle payment logic here
                handlePayment(address, selectedPaymentOption);
            }
        });
    }

    private void setPaymentOptionOnClickListener(TextView paymentOptionView, String paymentOption) {
        paymentOptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionCreditCard.setTextColor(Color.BLACK);
                optionDebitCard.setTextColor(Color.BLACK);
                optionCashOnDelivery.setTextColor(Color.BLACK);

                String customColorHex = "#169955";
                int customColor = Color.parseColor(customColorHex);

                // Highlight the selected payment option by changing text color to green
                paymentOptionView.setTextColor(customColor);
                selectedPaymentOption = paymentOption;
            }
        });
    }

    private void handlePayment(String address, String paymentOption) {
        // Payment handling logic goes here
        // For example, initiate payment processing using the address and selected payment option
        startActivity(new Intent(this,Sellerpayment.class));
        Toast.makeText(this, "Payment processing for address: " + address + " with Recieving option: " + paymentOption, Toast.LENGTH_SHORT).show();
    }
}
