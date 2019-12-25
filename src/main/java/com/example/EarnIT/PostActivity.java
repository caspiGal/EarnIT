package com.example.EarnIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PostActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private EditText description;
    private EditText price;
    private EditText email;
    private EditText phone;
    private Button postbtn;

    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        email = findViewById(R.id.emailText);
        phone = findViewById(R.id.phoneText);
        description = findViewById(R.id.description);
        price = findViewById(R.id.priceText);
        postbtn = findViewById(R.id.postButton);

        progressBar = findViewById(R.id.progressBar3);


        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                myRef = database.getInstance().getReference("Posts");
                String id = myRef.push().getKey();
                Post post = new Post(description.getText().toString(),price.getText().toString(),email.getText().toString(),phone.getText().toString());
                myRef.child(id).setValue(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PostActivity.this, "Post was added successfully", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(PostActivity.this, "Failed adding post", Toast.LENGTH_LONG).show();
                                description.setText("");
                                price.setText("");
                            }
                        });
                progressBar.setVisibility(View.GONE);
            }

        });


    }


}