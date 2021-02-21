package com.faraman_app.admin_faraman.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.faraman_app.admin_faraman.R;
import com.faraman_app.admin_faraman.activity.MessageActivity;
import com.faraman_app.admin_faraman.model.Chat;
import com.faraman_app.admin_faraman.model.Adviser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdvisersAdapter extends RecyclerView.Adapter<AdvisersAdapter.ViewHolder> {
    private Context mContext;
    private List<Adviser> mAdvisers;
    private boolean ischat;
    String theLastMessage;

    public AdvisersAdapter(Context mContext, List<Adviser> mAdvisers, boolean ischat){
        this.mAdvisers = mAdvisers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lawyer_item, parent, false);
        return new AdvisersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Adviser adviser = mAdvisers.get(position);
        holder.adviserName.setText(adviser.getAdviserName());
        if (adviser.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.default_person);
        } else {
            Glide.with(mContext).load(adviser.getImageURL()).into(holder.profile_image);
        }

        if (ischat){
            lastMessage(adviser.getId(), holder.last_msg);
        } else {
            holder.last_msg.setVisibility(View.GONE);
        }

        if (ischat){
            if (adviser.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("adviserId", adviser.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdvisers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView adviserName;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            adviserName = itemView.findViewById(R.id.adviserName);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }

    //check for last message
    private void lastMessage(final String adviserId, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseAdviser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseAdviser != null && chat != null) {
                        if (chat.getReceiver().equals(firebaseAdviser.getUid()) && chat.getSender().equals(adviserId) ||
                                chat.getReceiver().equals(adviserId) && chat.getSender().equals(firebaseAdviser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }

                switch (theLastMessage){
                    case  "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
