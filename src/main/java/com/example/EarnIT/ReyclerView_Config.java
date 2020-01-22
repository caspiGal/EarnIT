package com.example.EarnIT;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ReyclerView_Config {

    private Context mContext;
    private PostAdapter mPostAdapter;
    private NotificationCompat.Builder notification;
    private static final int uniqueID = 1245;


    public void setConfig(RecyclerView recyclerView, Context context, List<Post> posts, List<String> keys) {
        mContext = context;
        mPostAdapter = new PostAdapter(posts, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mPostAdapter);
        notification = new NotificationCompat.Builder(mContext,"CHANNEL ID");
        notification.setAutoCancel(true);
    }

    @NonNull
    public void setNotificationSend(View v){
        notification.setTicker("Ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("EarnIT");
        notification.setContentText("Job Applied Successfully");
        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        Intent intent = new Intent(mContext,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nm = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);

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
                    setNotificationSend(v);
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

