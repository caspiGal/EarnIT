package com.example.EarnIT;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ReyclerView_Config {

    private Context mContext;
    private PostAdapter mPostAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<Post> posts, List<String> keys) {
        mContext = context;
        mPostAdapter = new PostAdapter(posts, keys);
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

        public Button getApply() {
            return apply;
        }


        public void setApply(){
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Successfully", Toast.LENGTH_LONG).show();
                }
            });
        }


        public PostItemView(final ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.list_layout, parent, false));

            for (int i = 0; i < parent.getChildCount(); i++) {
                Drawable myDrawable = parent.getChildAt(i).getResources().getDrawable(R.drawable.book);
                parent.getChildAt(i).setBackground(myDrawable);
            }

            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            phoneText = (TextView) itemView.findViewById(R.id.phoneText);
            emailText = (TextView) itemView.findViewById(R.id.emailText);
            apply = (Button) itemView.findViewById(R.id.apply);


        }
            public void bind( final Post post, String key) {
                textViewDesc.setText("Description : " + post.getDescription());
                textViewPrice.setText("Price : " + post.getPrice());
                phoneText.setText("Phone : " + post.getPhone());
                emailText.setText("Email : " + post.getEmail());

                this.key = key;
            }
        }


        class PostAdapter extends RecyclerView.Adapter<PostItemView> {

            private List<Post> mPostList;
            private List<String> mKeys;


            public PostAdapter(List<Post> mPostList, List<String> mKeys) {
                this.mPostList = mPostList;
                this.mKeys = mKeys;
            }



            @NonNull
            @Override
            public PostItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                PostItemView result =  new PostItemView(parent);
                result.setApply();
                return result;
            }

            @Override
            public void onBindViewHolder(@NonNull PostItemView holder, int position) {
                holder.bind(mPostList.get(position), mKeys.get(position));
            }

            @Override
            public int getItemCount() {
                return mPostList.size();
            }
        }

    }

