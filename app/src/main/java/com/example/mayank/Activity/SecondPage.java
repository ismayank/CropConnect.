package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mayank.R;

public class SecondPage extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);

    }

    public void StartReg(View view) {
        Intent intent = new Intent(SecondPage.this, RegisterPage.class);
        startActivity(intent);
    }
    public void StartLog(View view) {
        Intent intent = new Intent(SecondPage.this, LoginPage.class);
        startActivity(intent);
    }
}
