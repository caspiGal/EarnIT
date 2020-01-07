package com.example.EarnIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText mEmail, mPassword;
    private Button mLoginBtn;
    private DatabaseReference myRef;
    private TextView mCreatebtn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;

    private int posterB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginBtn = findViewById(R.id.Loginbtn);
        mCreatebtn = findViewById(R.id.createView);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        myRef = database.getInstance().getReference("Users");


/*
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    User account  = dataSnapshot.getChildren().iterator().next()
                            .getValue(User.class);
                    posterB = account.getPermission();
                } catch (Throwable e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

 */

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email  = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password Is Required.");
                }
                if(password.length() < 6){
                    mPassword.setError("Password Must Be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("Testing", "dududududududududududu");
/*
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    try{
                                        User account  = dataSnapshot.getChildren().iterator().next()
                                                .getValue(User.class);
                                        Log.d("Testing", "onDataChange: user = "+account.getName());
                                        posterB = account.getPermission();
                                    } catch (Throwable e) {

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

 */
                            Log.d("Testing", "UID = "+fAuth.getCurrentUser().getEmail());
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getCurrentUser().getUid());
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (user.getPermission() == 1) {
                                        Toast.makeText(Login.this, "Login Successfully , Hello Poster", Toast.LENGTH_SHORT).show();
                                        Intent j = new Intent(getApplicationContext(), PostActivity.class);
                                        startActivity(j);
                                    } else {
                                        Toast.makeText(Login.this, "Login Successfully , Hello Searcher", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

/*
                            Log.d("Testing", "onComplete: posterB = "+posterB);
                            if (posterB == 1) {
                                Toast.makeText(Login.this, "Login Successfully , Hello Poster", Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(getApplicationContext(), PostActivity.class);
                                startActivity(j);
                            } else {
                                Toast.makeText(Login.this, "Login Successfully , Hello Searcher", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }

 */
                        }
                        else{
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }

        });

        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}