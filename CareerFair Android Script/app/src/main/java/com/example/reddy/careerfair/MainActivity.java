package com.example.reddy.careerfair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button slog, sregistration, elog, eregistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void stdlogin(View view) {
        Intent intent = new Intent(this,Student_login.class);
        startActivity(intent);
    }

    public void stdreg(View view) {
        Intent intent = new Intent(this,student_register.class);
        startActivity(intent);
    }

    public void emplogin(View view) {
        Intent intent = new Intent(this,Employer_login.class);
        startActivity(intent);
    }

    public void empreg(View view) {
        Intent intent = new Intent(this,employer_register.class);
        startActivity(intent);
    }
}
