package com.example.mayank.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayank.Helper.SessionManager;
import com.example.mayank.Helper.UserdbHelper;
import com.example.mayank.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends BaseActivity {
    SessionManager sessionManager;

    private EditText sellFirstName;
    private EditText sellLastName;
    private EditText sellEmail;
    private EditText sellPass;
    UserdbHelper userdbHelper ;


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        sessionManager = new SessionManager(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        sellFirstName = (EditText) findViewById(R.id.userFirstName);
        sellLastName = (EditText)findViewById(R.id.userLastName);
        sellEmail = (EditText)findViewById(R.id.userEmail);
        sellPass = (EditText)findViewById(R.id.userPass);


        userdbHelper=new UserdbHelper(getApplicationContext());

    }
    public void loginnow(View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void StartLog(View view) {
        String name1 = sellFirstName.getText().toString();
        String name2 = sellLastName.getText().toString();
        String email1 = sellEmail.getText().toString();
        String pass1 = sellPass.getText().toString();




        if (TextUtils.isEmpty(name1)) {
            sellFirstName.setError("First name is required");
            sellFirstName.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(email1)) {
            sellEmail.setError("Email is required");
            sellEmail.requestFocus();
            return;
        }

        // Validate email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            sellEmail.setError("Please enter a valid email address");
            sellEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass1)) {
            sellPass.setError("Password is required");
            sellPass.requestFocus();
            return;
        }

        if (userdbHelper.isEmailExists(email1))
        {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();


        }
        else {
            Toast.makeText(this, "Email is available", Toast.LENGTH_SHORT).show();

            boolean b = userdbHelper.registeruser(name1,name2,email1,pass1);
            if(b == true){
                sessionManager.createSession(email1);
                Toast.makeText(this,"User Registered",Toast.LENGTH_LONG).show();
                sellFirstName.setText("");
                sellLastName.setText("");
                sellEmail.setText("");
                sellPass.setText("");
                startActivity(new Intent(RegisterPage.this, LoginPage.class));

            }
            else{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();

            }
        }


    }

}