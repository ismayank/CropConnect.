package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayank.Domain.UserModel;
import com.example.mayank.Helper.SessionManager;
import com.example.mayank.Helper.UserdbHelper;
import com.example.mayank.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Dashboard extends BaseActivity {

    TextView usernameText,addressText;
    SessionManager sessionManager;
    UserdbHelper userdbHelper;
    UserModel userModel;

    String email1;
    String namey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       userdbHelper = new UserdbHelper(Dashboard.this);

        sessionManager = new SessionManager(getApplicationContext());

        usernameText = (TextView) findViewById(R.id.usernameText);
        addressText = (TextView) findViewById(R.id.addressText);

        String name = sessionManager.getSessionDetails("key_session_email");
        addressText.setText(name);

        email1= name;

        getDetails();

    }

    private void getDetails() {

        ArrayList<UserModel> al = userdbHelper.getloggedinDetails(email1);

        userModel = al.get(0);
        namey=userModel.getFirstname();
        usernameText.setText(userModel.getFirstname());
        addressText.setText(userModel.getEmail());

    }
    public void settings(View view){
        Intent intent = new Intent(this, ProfileSetting.class);
        intent.putExtra("key_name",namey);
        startActivity(intent);

    }

    public void logoutmain(View view) {
        sessionManager.logoutSession();
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);

    }
    public void checkHistory(View view){

        Intent intent = new Intent(Dashboard.this, Historyorder.class);
        intent.putExtra("key_email",email1);
        startActivity(intent);

    }
    public void notifications(View view){

        Intent intent = new Intent(Dashboard.this, NotificationActivity.class);
        startActivity(intent);

    }

    public void toggleFaqSection(View view) {
        LinearLayout faqContentLayout = findViewById(R.id.faqContentLayout);
        ImageView expandFaqButton = findViewById(R.id.expandFaqButton);

        if (faqContentLayout.getVisibility() == View.GONE) {
            faqContentLayout.setVisibility(View.VISIBLE);
            expandFaqButton.setImageResource(R.drawable.ic_arrow_up); // Change arrow icon to point up
        } else {
            faqContentLayout.setVisibility(View.GONE);
            expandFaqButton.setImageResource(R.drawable.ic_arrow_down); // Change arrow icon to point down
        }
    }
    public void toggleContactOptions(View view) {
        LinearLayout contactOptionsLayout = findViewById(R.id.contactOptionsLayout);
        if (contactOptionsLayout.getVisibility() == View.GONE) {
            contactOptionsLayout.setVisibility(View.VISIBLE);
        } else {
            contactOptionsLayout.setVisibility(View.GONE);
        }
    }


}