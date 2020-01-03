package com.example.EarnIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private EditText mFullName, mEmail, mPassword , phoneNumber;
    private Button mRegisterbtn;
    private TextView mLoginBtn;
    private boolean permission;
    private Switch s;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mRegisterbtn = findViewById(R.id.Registerbtn);
        mLoginBtn = findViewById(R.id.registerView);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        phoneNumber = findViewById(R.id.editText);
        s = findViewById(R.id.s);
        s.setTextOn("Poster");
        s.setTextOff("Seacrcher");

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String email  = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();

                s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            permission = true;
                        } else {
                            permission = false;
                        }
                    }
                });

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Is Required.");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    phoneNumber.setError("Phone Is Required.");
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

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    myRef = database.getInstance().getReference("Users");
                                    String id = myRef.push().getKey();
                                    User user = new User(id,mFullName.getText().toString(),mEmail.getText().toString(),mPassword.getText().toString(),phoneNumber.getText().toString(),permission);
                                    myRef.child(id).setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Register.this, "User was added successfully", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(Exception e) {
                                                    Toast.makeText(Register.this, "User adding post", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                    Toast.makeText(Register.this,"User Created.",Toast.LENGTH_SHORT).show();
                                    move_to_main();
                        }
                        else{
                            Toast.makeText(Register.this,"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    public void move_to_main(){
        Intent i = new Intent(getApplicationContext(),Login.class);
        startActivity(i);
    }

}

