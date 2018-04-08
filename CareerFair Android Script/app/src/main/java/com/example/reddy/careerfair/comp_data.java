package com.example.reddy.careerfair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class comp_data extends AppCompatActivity {

    private TextView mtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_data);

        mtextview = (TextView)findViewById(R.id.text_data);
        mtextview.setText("Hello, we are a payment company looking out for some smart developers to work with us for this summer." +
                "We are looking for interns in bachelors, masters and phd and need to have a minimum gpa of 3.0");
    }
}
