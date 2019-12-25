package com.example.EarnIT;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.List;

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

        private String key;

        public PostItemView(ViewGroup parent ){
            super(LayoutInflater.from(mContext).inflate(R.layout.list_layout,parent,false));

            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            phoneText = (TextView) itemView.findViewById(R.id.phoneText);
            emailText = (TextView) itemView.findViewById(R.id.emailText);

        }

        public void bind( Post post, String key){
            textViewDesc.setText("Description : " + post.getDescription());
            textViewPrice.setText("Price : " + post.getPrice());
            phoneText.setText("Phone : " + post.getPhone());
            emailText.setText("Email : " + post.getEmail());
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
