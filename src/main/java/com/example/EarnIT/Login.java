package com.example.EarnIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLoginBtn;
    private TextView mCreatebtn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;


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
                        if(task.isSuccessful()){
                            if(mEmail.getText().toString().equals("elyashivderi17@gmail.com") || mEmail.getText().toString().equals("caspigal7@gmail.com") || mEmail.getText().toString().equals("liozelmalem@gmail.com")){
                                Toast.makeText(Login.this,"Hello Manager , What Would You Like To Do Today ? ",Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(getApplicationContext(),ManagerActivity.class);
                                startActivity(j);
                            }
                            else {
                                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }
                        }
                        else{
                            Toast.makeText(Login.this,"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
