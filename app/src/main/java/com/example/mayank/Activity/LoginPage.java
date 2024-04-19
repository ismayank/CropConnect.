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

import com.example.mayank.Helper.SessionManager;
import com.example.mayank.Helper.UserdbHelper;
import com.example.mayank.R;

public class LoginPage extends BaseActivity {
    private EditText edEmailLogin;
    private EditText edPasswordLogin;
    SessionManager sessionManager;
    UserdbHelper userdbHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sessionManager = new SessionManager(getApplicationContext());

        edEmailLogin = findViewById(R.id.edEmailLogin);
        edPasswordLogin = findViewById(R.id.edPasswordLogin);

        userdbHelper = new UserdbHelper(getApplicationContext());

        Button buttonLoginLogin = (Button) findViewById(R.id.buttonLoginLogin);

        userdbHelper=new UserdbHelper(getApplicationContext());

        buttonLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1= edEmailLogin.getText().toString();
                String pass1= edPasswordLogin.getText().toString();

                // Validate the input fields
                if (TextUtils.isEmpty(email1)) {
                    edEmailLogin.setError("Email is required");
                    edEmailLogin.requestFocus();
                    return;
                }

                // Validate email format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    edEmailLogin.setError("Please enter a valid email address");
                    edEmailLogin.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pass1)) {
                    edPasswordLogin.setError("Password is required");
                    edPasswordLogin.requestFocus();
                    return;
                }



                boolean b =userdbHelper.userlogin(email1,pass1);
                if(b){
                    sessionManager.createSession(email1);

                    Toast.makeText(LoginPage.this,"Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("key_email",email1);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Not matched",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void tvDontHaveAccount(View view){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);

    }
}
