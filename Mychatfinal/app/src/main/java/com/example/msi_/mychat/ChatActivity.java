package com.example.msi_.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    TextView person_name, person_email;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<Show_Chat_Activity_Data_Items, Show_Chat_ViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_chat_layout);

        firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = firebaseDatabase.getReference("Users_phone");

        progressBar = (ProgressBar) findViewById(R.id.show_chat_progressBar2);

        recyclerView = (RecyclerView)findViewById(R.id.show_chat_recyclerView);

        mLinearLayoutManager = new LinearLayoutManager(ChatActivity.this);

        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onStart() {

        super.onStart();
        progressBar.setVisibility(ProgressBar.VISIBLE);

            mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_Chat_Activity_Data_Items, Show_Chat_ViewHolder>(Show_Chat_Activity_Data_Items.class, R.layout.show_chat_single_item, Show_Chat_ViewHolder.class, myRef) {

                public void populateViewHolder(final Show_Chat_ViewHolder viewHolder, Show_Chat_Activity_Data_Items model, final int position) {

                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                    if (!model.getName().equals("Null")) {
                        viewHolder.Person_Name(model.getName());
                        viewHolder.Person_Image(model.getImage_Url());

                        if(model.getPhone().equals(SignIn.LoggedIn_User_Phone)) {

                            viewHolder.Layout_hide();
                        }
                        else
                            viewHolder.Person_Phone(model.getPhone());
                    }

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(final View v) {

                            DatabaseReference ref = mFirebaseAdapter.getRef(position);
                            ref.addValueEventListener(new ValueEventListener() {
                             @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    String retrieve_name = dataSnapshot.child("Name").getValue(String.class);
                                    String retrieve_Email = dataSnapshot.child("Phone").getValue(String.class);
                                    String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(), ChatConversationActivity.class);
                                    intent.putExtra("image_id", retrieve_url);
                                    intent.putExtra("phone", retrieve_Email);
                                    intent.putExtra("name", retrieve_name);

                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                             });
                        }
                    });
                }
            };

            recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class Show_Chat_ViewHolder extends RecyclerView.ViewHolder {

        private final TextView person_name, person_phone;
        private final ImageView person_image;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public Show_Chat_ViewHolder(final View itemView) {
            super(itemView);
            person_name = (TextView) itemView.findViewById(R.id.chat_persion_name);
            person_phone = (TextView) itemView.findViewById(R.id.chat_persion_phone);
            person_image = (ImageView) itemView.findViewById(R.id.chat_persion_image);
            layout = (LinearLayout)itemView.findViewById(R.id.show_chat_single_item_layout);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        private void Person_Name(String title) {

            person_name.setText(title);
        }
        private void Layout_hide() {

            params.height = 0;
            layout.setLayoutParams(params);

        }

        private void Person_Phone(String title) {
            person_phone.setText(title);
        }

        private void Person_Image(String url) {

            if (!url.equals("Null")) {
                Glide.with(itemView.getContext())
                        .load(url)
                        .crossFade()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.loading)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(person_image);
            }

        }


    }

}
