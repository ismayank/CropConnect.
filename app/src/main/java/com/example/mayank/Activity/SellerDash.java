package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mayank.R;

public class SellerDash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dash);
    }
    public void logout1(View view){
        startActivity(new Intent(this,FirstPage.class));
    }
}