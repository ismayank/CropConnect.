package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayank.Helper.SellerdbHelper;
import com.example.mayank.Helper.UserdbHelper;
import com.example.mayank.R;

public class sellerLogin extends BaseActivity {
    private EditText sellEmailLogin;
    private EditText sellPassLogin;
    SellerdbHelper sellerdbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        sellEmailLogin = findViewById(R.id.sellEmailLogin);
        sellPassLogin = findViewById(R.id.sellPassLogin);



        Button sellbuttonLogin = (Button) findViewById(R.id.sellbuttonLogin);

        sellerdbHelper=new SellerdbHelper(getApplicationContext());

        sellbuttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email1= sellEmailLogin.getText().toString();
                String pass1= sellPassLogin.getText().toString();

                // Validate the input fields
                if (TextUtils.isEmpty(email1)) {
                    sellEmailLogin.setError("Email is required");
                    sellEmailLogin.requestFocus();
                    return;
                }

                // Validate email format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    sellEmailLogin.setError("Please enter a valid email address");
                    sellEmailLogin.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pass1)) {
                    sellPassLogin.setError("Password is required");
                    sellPassLogin.requestFocus();
                    return;
                }


                boolean b =sellerdbHelper.selllogin(email1,pass1);
                if(b){
                    Intent intent = new Intent(getApplicationContext(),SellerActivity.class);
                    Toast.makeText(sellerLogin.this,"Login Successful",Toast.LENGTH_LONG).show();
                    intent.putExtra("key_email",email1);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Not matched",Toast.LENGTH_LONG).show();
                }

            }
        });
        ;
    }
}