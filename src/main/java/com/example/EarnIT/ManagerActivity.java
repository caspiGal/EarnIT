package com.example.EarnIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerActivity extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    Button emptyPostbtn;
    Button report;
    Button moveMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        emptyPostbtn = findViewById(R.id.emptyPostbtn);
        report = findViewById(R.id.report);
        moveMain = findViewById(R.id.moveMain);

        emptyPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManagerActivity.this,"Remove The Last Post ...",Toast.LENGTH_SHORT).show();
                ref = database.getInstance().getReference("Posts");
                String key = ref.push().getKey();
                ref.child(key).removeValue();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManagerActivity.this,"Report ...",Toast.LENGTH_SHORT).show();
            }
        });

        moveMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManagerActivity.this,"Move To Jobs View ...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
