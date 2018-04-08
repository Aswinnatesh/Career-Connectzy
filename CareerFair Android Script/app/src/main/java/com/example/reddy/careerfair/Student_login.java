package com.example.reddy.careerfair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Student_login extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        email = (EditText)findViewById(R.id.sl_email);
        password = (EditText)findViewById(R.id.sl_password);
        btn_login = (Button)findViewById(R.id.sl_button);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String semail = email.getText().toString();
                Log.d("email", "email is " + semail);
                final String spwd = password.getText().toString();

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

                        stitchClient.logInWithProvider(new EmailPasswordAuthProvider(semail, spwd)).continueWithTask(
                                new Continuation<String, Task<Document>>() {
                                    @Override
                                    public Task<Document> then(@NonNull Task<String> task) throws Exception {
                                        final Document updateDoc = new Document(
                                                "owner_id",
                                                task.getResult()
                                        );

                                        updateDoc.put("number", 42);
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
                                    Intent intent = new Intent(getApplicationContext(),Student_update.class);
                                    intent.putExtra("email","lappy176@gmail.com");
                                    intent.putExtra("password","svtr@1995");
                                    startActivity(intent);
                                    return;
                                }
                                Log.e("STITCH", task.getException().toString());
                            }
                        });
                    }
                });
            }
        });
    }
}