package com.example.EarnIT;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference postR;
    private List<Post> posts = new ArrayList<>();



    public interface  DataStatus{
        void  DataIsLoaded(List<Post> posts , List<String> keys );
        void  DataIsInserted();
        void  DataIsDeleted();

}

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        postR = mDatabase.getReference("Posts");
    }


    public void readPosts(final DataStatus dataStatus){
        postR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Post post = keyNode.getValue(Post.class);
                    posts.add(post);
                }

                dataStatus.DataIsLoaded(posts,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
