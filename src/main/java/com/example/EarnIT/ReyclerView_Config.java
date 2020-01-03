package com.example.EarnIT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Random;

public class ReyclerView_Config {

    private Context mContext;
    private PostAdapter mPostAdapter;
    public void setConfig(RecyclerView recyclerView, Context context,List<Post> posts,List<String>keys ){
        mContext = context;
        mPostAdapter = new PostAdapter(posts,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mPostAdapter);
    }

    class PostItemView extends RecyclerView.ViewHolder {

        private TextView textViewDesc;
        private TextView textViewPrice;
        private TextView phoneText;
        private TextView emailText;
        private Button apply;

        private String key;

        public PostItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.list_layout,parent,false));

            for (int i = 0;i < parent.getChildCount();i++){
                Drawable myDrawable = parent.getChildAt(i).getResources().getDrawable(R.drawable.book);
                parent.getChildAt(i).setBackground(myDrawable);
             }

            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            phoneText = (TextView) itemView.findViewById(R.id.phoneText);
            emailText = (TextView) itemView.findViewById(R.id.emailText);
            apply = (Button) itemView.findViewById(R.id.apply);


        }

        public void bind( Post post, String key){
            textViewDesc.setText("Description : " + post.getDescription());
            textViewPrice.setText("Price : " + post.getPrice());
            phoneText.setText("Phone : " + post.getPhone());
            emailText.setText("Email : " + post.getEmail());
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Applying successfully ..." ,Toast.LENGTH_SHORT).show();
                }
            });
            this.key = key;
        }
    }


    class PostAdapter extends RecyclerView.Adapter<PostItemView>{

        private List<Post> mPostList;
        private List<String> mKeys;

        public PostAdapter(List<Post> mPostList, List<String> mKeys) {
            this.mPostList = mPostList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public PostItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PostItemView holder, int position) {
            holder.bind(mPostList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mPostList.size();
        }
    }

}
