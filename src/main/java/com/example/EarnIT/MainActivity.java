package com.example.EarnIT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import com.example.EarnIT.Register;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Post> posts = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    DatabaseReference myRef_for_users;
    TextView welcome;
    String currentUserID;
    private RecyclerView mRecyclerView;

    Button postbtn;
    Button logOutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        welcome = findViewById(R.id.welcome);
        myRef = database.getInstance().getReference("Posts");
        myRef_for_users = database.getInstance().getReference("Users");
        logOutbtn = findViewById(R.id.logOutbtn);
        postbtn = findViewById(R.id.postbtn);
        currentUserID = mAuth.getCurrentUser().getUid();
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerViewJob);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        FirebaseDatabaseHelper FH = new FirebaseDatabaseHelper();

        FH.readPosts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Post> posts ,List<String> keys) {
                new ReyclerView_Config().setConfig(mRecyclerView, MainActivity.this, posts, keys);

            }

            @Override
            public void DataIsInserted() {
            }

            @Override
            public void DataIsDeleted() {
            }
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
