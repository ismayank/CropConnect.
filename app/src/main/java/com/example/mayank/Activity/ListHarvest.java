package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mayank.R;

public class ListHarvest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_harvest);
    }
    public void StartsellReg(View view) {
        Intent intent = new Intent(this, SellerReg.class);
        startActivity(intent);
    }
    public void StartsellLog(View view) {
        Intent intent = new Intent(this, sellerLogin.class);
        startActivity(intent);
    }
}