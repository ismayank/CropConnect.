package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mayank.Helper.SessionManager;
import com.example.mayank.R;

public class FirstPage extends BaseActivity {

    Button buttonStart;
    Button ListStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        SessionManager sessionManager=new SessionManager(getApplicationContext());
        boolean b =sessionManager.checkSession();

        // Find the button after setting the content view
        buttonStart = findViewById(R.id.buttonStart);
        ListStart = findViewById(R.id.buttonStart2);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(b == true){

                    Intent intent = new Intent(FirstPage.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(FirstPage.this, SecondPage.class);
                    startActivity(intent);

                }

            }
        });

        ListStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstPage.this, ListHarvest.class);
                startActivity(intent);
            }
        });
    }

}
