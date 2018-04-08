package com.example.reddy.careerfair;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.sparkpost.transport.IRestConnection;
import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;

public class Student_dashboard extends Activity {

    private static final String TAG = "MainActivity";
    private static final String API_KEY = "bfec5c89e03eed77a9c047f0b6c06d4d83697918";
    private static final String SENDER_EMAIL = "lappy176@gmail.com";
    private static final String RECIPIENT_EMAIL = "aswin@gmail.com";
    private static final String SUBJECT = "Submit the Resume - Stony Brook university";
    private static final String CONTENT = "https://github.com/HonzaR/SparkPostUtil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ListView lstview=(ListView)findViewById(R.id.listv);
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(),comp_data.class);
                startActivity(intent);
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] items={"Hi! You are near Paypal"};
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.list_item,R.id.txt,items);
        // Bind data to the ListView
        lstview.setAdapter(adapter);

    }

    public void clickMe(View view) throws SparkPostException{
        Button bt=(Button)view;
        String html = "<html><body>Hi , it was nice meeting you at the fair!</body></html>";
        String replyTo = "lappy176@gmail.com";

        Log.d("Activity","I am in dashboard");

        SendEmail se = new SendEmail();


    }

}

