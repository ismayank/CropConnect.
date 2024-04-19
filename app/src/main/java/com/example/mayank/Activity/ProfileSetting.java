package com.example.mayank.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayank.Helper.SessionManager;
import com.example.mayank.Helper.UserdbHelper;
import com.example.mayank.R;

public class ProfileSetting extends BaseActivity {
    SessionManager sessionManager;
    UserdbHelper userdbHelper;

    EditText updtname;
    TextView updtemail;
    String email;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        userdbHelper = new UserdbHelper(getApplicationContext());

        sessionManager = new SessionManager(getApplicationContext());
        updtname = findViewById(R.id.updtname);
        updtemail = findViewById(R.id.updtemail);

       email = sessionManager.getSessionDetails("key_session_email");
        String name= getIntent().getStringExtra("key_name");
        updtname.setText(name);
        updtemail.setText(email);



    }
    public void Updatenow(View view){
        String name1 = updtname.getText().toString();
        boolean b =userdbHelper.updateUser(email,name1);
        if(b){
            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        }


    }
    public void backprofile(View view){
        finish();
    }
    public void Deletenow(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Profile");
        builder.setMessage("Are You Sure?");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean b = userdbHelper.deleteProfileHelper(email);
                if(b){
                    sessionManager.logoutSession();
                    Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RegisterPage.class));

                }
                else{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

                }
            }
        });

        builder.show();

    }

}