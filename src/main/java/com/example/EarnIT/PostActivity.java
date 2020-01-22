package com.example.EarnIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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
    private Button logOutbtn;
    private String currentUserId;

    private NotificationCompat.Builder notification;
    private static final int uniqueID = 1394;

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
        logOutbtn = findViewById(R.id.logOutbtn);
        fAuth = FirebaseAuth.getInstance();
        currentUserId = fAuth.getCurrentUser().getUid();

        notification = new NotificationCompat.Builder(this,"CHANNEL ID");
        notification.setAutoCancel(true);

        myRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    User account  = dataSnapshot.getValue(User.class);
                    nameTextView.setText("Welcome " + account.getName());
                    email.setText(account.getEmail());
                    phone.setText(account.getPhone());
                } catch (Throwable e) {
                    e.printStackTrace();
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

                setNotificationSend(v);

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
                Post post = new Post(currentUserId,description.getText().toString(),price.getText().toString(),email.getText().toString(),phone.getText().toString());
                myRef.child(id).setValue(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PostActivity.this, "Post was added successfully", Toast.LENGTH_LONG).show();
                                description.setText("");
                                price.setText("");
                                progressBar.setVisibility(View.GONE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(PostActivity.this, "Failed adding post", Toast.LENGTH_LONG).show();
                                description.setText("");
                                price.setText("");
                                progressBar.setVisibility(View.GONE);
                            }
                        });

            }

        });

        progressBar.setVisibility(View.GONE);

        logOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText("");
                price.setText("");
                logout(v);
            }
        });

    }

    @NonNull
    public void setNotificationSend(View v){
        notification.setTicker("Ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("EarnIT");
        notification.setContentText("Post Added Successfully");
        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        Intent intent = new Intent(this,PostActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        // channel adding first
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId("com.myApp");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.myApp",
                    "My App",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }
        try {
            nm.notify(uniqueID,notification.build());
        }
        catch(Throwable error){
            error.printStackTrace();
        }
    }

    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}