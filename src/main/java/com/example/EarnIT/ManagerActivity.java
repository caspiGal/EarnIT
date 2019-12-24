package com.example.EarnIT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerActivity extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    Button emptyPostbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        emptyPostbtn = findViewById(R.id.emptyPostbtn);

        emptyPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = database.getInstance().getReference("Posts");
                String key = ref.push().getKey();
                ref.child(key).setValue("");
            }
        });

    }
}
