package com.example.EarnIT;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private TextView welcome;
    private RecyclerView mRecyclerView;
    private Button postbtn;
    private Button logOutbtn;

    private String currentName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        welcome = findViewById(R.id.welcome);
        myRef = database.getInstance().getReference("Posts");
        userRef = database.getInstance().getReference("Users");
        logOutbtn = findViewById(R.id.logOutbtn);
        postbtn = findViewById(R.id.postbtn);
        mRecyclerView = findViewById(R.id.RecyclerViewJob);
        FirebaseDatabaseHelper FH = new FirebaseDatabaseHelper();
        final String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        welcome.append("Welcome ");

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    User account  = dataSnapshot.getChildren().iterator().next()
                            .getValue(User.class);
                    currentName = account.getName();
                    welcome.append(currentName);
                } catch (Throwable e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


        FH.readPosts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Post> posts ,List<String> keys) {
                new ReyclerView_Config().setConfig(mRecyclerView, MainActivity.this, posts, keys);
            }
            @Override
            public void DataIsInserted() { }
            @Override
            public void DataIsDeleted() { }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PostActivity.class));
            }
        });



        logOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });


    }

    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}
