package com.example.reddy.careerfair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.StitchClientFactory;
import com.mongodb.stitch.android.auth.emailpass.EmailPasswordAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;

import org.bson.Document;

import java.util.List;

public class Student_update extends AppCompatActivity {

    private EditText name, mobile;
    private CheckBox senior, master, phd, java, python, mongo;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update);

        name = (EditText)findViewById(R.id.sr_name);
        mobile = (EditText)findViewById(R.id.sr_mobile);
        senior = (CheckBox)findViewById(R.id.checkbox_senior);
        master = (CheckBox)findViewById(R.id.checkbox_masters);
        phd = (CheckBox)findViewById(R.id.checkbox_phd);
        java = (CheckBox)findViewById(R.id.checkbox_java);
        python = (CheckBox)findViewById(R.id.checkbox_python);
        mongo = (CheckBox)findViewById(R.id.checkbox_Mongo);

    }

    public void stdUpdate(View view) {

        final String sname = name.getText().toString();
        final String smobile = mobile.getText().toString();
        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        String degree = "";
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

        final String finalDegree = degree;
        final String finalSkill = skill;
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

                stitchClient.logInWithProvider(new EmailPasswordAuthProvider(email, password)).continueWithTask(
                        new Continuation<String, Task<Document>>() {
                            @Override
                            public Task<Document> then(@NonNull Task<String> task) throws Exception {
                                final Document updateDoc = new Document(
                                        "owner_id",
                                        task.getResult()
                                );

                                updateDoc.put("name", sname);
                                updateDoc.put("mobile", smobile);
                                updateDoc.put("degree", finalDegree);
                                updateDoc.put("skills", finalSkill);
                                return coll.updateOne(null, updateDoc, true);
                            }
                        }
                ).continueWithTask(new Continuation<Document, Task<List<Document>>>() {
                    @Override
                    public Task<List<Document>> then(@NonNull Task<Document> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return coll.find(new Document("owner_id", stitchClient.getUserId()), 100);
                    }
                }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Document>> task) {
                        if (task.isSuccessful()) {
                            Log.d("STITCH", task.getResult().toString());
                            Intent intent = new Intent(getApplicationContext(),Student_dashboard.class);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            startActivity(intent);
                            return;
                        }
                        Log.e("STITCH", task.getException().toString());
                    }
                });
            }
        });

    }
}
