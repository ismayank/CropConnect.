package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mayank.Helper.SessionManager;
import com.example.mayank.R;

public class OrderSuccess extends BaseActivity {
    String email1;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        sessionManager = new SessionManager(getApplicationContext());

        String name = sessionManager.getSessionDetails("key_session_email");
        email1 = name;
    }
    public void HomePage(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}