package com.example.mayank.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayank.Helper.SellerdbHelper;
import com.example.mayank.R;

public class SellerReg extends BaseActivity {
    private EditText sellFirstName;
    private EditText sellLastName;
    private EditText sellEmail;
    private EditText sellPass;

    SellerdbHelper sellerdbHelper;
    private Button regBtn1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_reg);

        sellFirstName = findViewById(R.id.sellFirstName);
        sellLastName = findViewById(R.id.sellLastName);
        sellEmail = findViewById(R.id.sellEmail);
        sellPass = findViewById(R.id.sellPass);
        regBtn1 = findViewById(R.id.regBtn1);

        sellerdbHelper=new SellerdbHelper(getApplicationContext());

    }
    public void SellerLog(View view){
        String name1 = sellFirstName.getText().toString();
        String name2 = sellLastName.getText().toString();
        String email1 = sellEmail.getText().toString();
        String pass1 = sellPass.getText().toString();


        // Validate first name
        if (TextUtils.isEmpty(name1)) {
            sellFirstName.setError("First name is required");
            sellFirstName.requestFocus();
            return;
        }


        // Validate email format
        if (TextUtils.isEmpty(email1) || !Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            sellEmail.setError("Please enter a valid email address");
            sellEmail.requestFocus();
            return;
        }

        // Validate password
        if (TextUtils.isEmpty(pass1)) {
            sellPass.setError("Password is required");
            sellPass.requestFocus();
            return;
        }

        boolean b = sellerdbHelper.registersell(name1,name2,email1,pass1);
        if(b == true){
            Toast.makeText(this,"Seller Registered",Toast.LENGTH_LONG).show();
            sellFirstName.setText("");
            sellLastName.setText("");
            sellEmail.setText("");
            sellPass.setText("");
            startActivity(new Intent(SellerReg.this, sellerLogin.class));

        }
        else{
            Toast.makeText(this,"Erroe",Toast.LENGTH_LONG).show();

        }
    }

}