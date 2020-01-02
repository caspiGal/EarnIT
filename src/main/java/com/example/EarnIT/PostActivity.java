
package com.example.EarnIT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PostActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth fAuth;
    private DatabaseReference myRef;
    private TextView description;
    private TextView price;
    private TextView email;
    private TextView phone;
    private Button postbtn;

    private ProgressBar progressBar;
    private TextView nameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        price = findViewById(R.id.priceText);
        postbtn = findViewById(R.id.postButton);
        progressBar = findViewById(R.id.progressBar3);
        myRef = database.getReference("Users");
        nameTextView = findViewById(R.id.name);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    User account  = dataSnapshot.getChildren().iterator().next()
                            .getValue(User.class);
                    nameTextView.setText(account.getName());
                    email.setText(account.getEmail());
                    phone.setText(account.getPhone());
                } catch (Throwable e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mprice  = price.getText().toString().trim();
                String mdescription = description.getText().toString().trim();

                if(TextUtils.isEmpty(mprice)){
                    price.setError("Price Is Required.");
                    return;
                }
                if(TextUtils.isEmpty(mdescription)){
                    description.setError("Description Is Required.");
                    return;
                }


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
            }

        });

        progressBar.setVisibility(View.GONE);

    }


}