package com.example.reddy.careerfair;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.StitchClientFactory;
import com.mongodb.stitch.android.auth.anonymous.AnonymousAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.auth.emailpass.EmailPasswordAuthProvider;

import org.bson.Document;

import java.util.List;

public class student_register extends AppCompatActivity {

    private EditText name, email, password, mobile;
    private CheckBox senior, master, phd, java, python, mongo;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        name = (EditText) findViewById(R.id.sr_name);
        email = (EditText) findViewById(R.id.sr_email);
        password = (EditText) findViewById(R.id.sr_password);
        mobile = (EditText) findViewById(R.id.sr_mobile);
        senior = (CheckBox) findViewById(R.id.checkbox_senior);
        master = (CheckBox) findViewById(R.id.checkbox_masters);
        phd = (CheckBox) findViewById(R.id.checkbox_phd);
        java = (CheckBox) findViewById(R.id.checkbox_java);
        python = (CheckBox) findViewById(R.id.checkbox_python);
        mongo = (CheckBox) findViewById(R.id.checkbox_Mongo);
        register = (Button) findViewById(R.id.sreg);

        String s_name = name.getText().toString();
        final String s_email = email.getText().toString();
        String s_mobile = mobile.getText().toString();
        final String s_pass = password.getText().toString();
        String degree;
        String skill = "";
        if (master.isChecked()) {
            degree = "Masters";
        } else if (senior.isChecked()) {
            degree = "Senior";
        } else if (phd.isChecked()) {
            degree = "Phd";
        }
        if (java.isChecked()) {
            skill += "Java";
        } else if (python.isChecked()) {
            skill += "Python";
        } else if (mongo.isChecked()) {
            skill += "mongo";
        }

    }

    public void stdRegister(View view) {

        StitchClientFactory.create(getApplicationContext(), "careerfair-qgltk").addOnCompleteListener(new OnCompleteListener<StitchClient>() {
            @Override
            public void onComplete(@NonNull final Task<StitchClient> task) {
                if (!task.isSuccessful()) {
                    Log.e("STITCH", "error creating Stitch client", task.getException());
                    return;
                }

                final StitchClient stitchClient = task.getResult();
                final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
                final MongoClient.Collection coll = mongoClient.getDatabase("Users").getCollection("Data");

                stitchClient.register("lappy176@gmail.com", "123456").addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull final Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            Log.d("stitch", "Successfully sent account confirmation email");
                                    /* code to direct user to check their email */
                        } else {
                            Log.e("stitch", "Error registering new user:", task.getException());
                        }
                    }
                });
            }
        });

    }
}
