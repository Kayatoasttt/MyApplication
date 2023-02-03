package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private ArrayList<Message> messages;
    private String senderImg, recipientImg;
    private Context context;

    public MessageAdapter(ArrayList<Message> messages, String senderImg, String recipientImg, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.recipientImg = recipientImg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_holder, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.txtMessage.setText(messages.get(position).getContent());
        holder.txtDateTime.setText(messages.get(position).getDateTime());

        ConstraintLayout constraintLayout = holder.ccLayout;

        if(messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            holder.txtMessage.setBackgroundResource(R.drawable.s_message_bg);
            Glide.with(context).load(senderImg).error(R.drawable.default_profile).placeholder(R.drawable.default_profile).into(holder.profileImage);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout); // clones the main cc layout(contains the message and the profile image)
            constraintSet.clear(R.id.profileCardView,ConstraintSet.LEFT);
            constraintSet.clear(R.id.txtMessage,ConstraintSet.LEFT);
            constraintSet.connect(R.id.profileCardView,ConstraintSet.RIGHT,R.id.ccLayout,ConstraintSet.RIGHT,0);
            constraintSet.connect(R.id.txtMessage,ConstraintSet.RIGHT,R.id.profileCardView,ConstraintSet.LEFT,0);
            constraintSet.connect(R.id.txtDateTime,ConstraintSet.RIGHT,R.id.profileCardView,ConstraintSet.LEFT,0);
            constraintSet.applyTo(constraintLayout);
        } else {
            ConstraintSet constraintSet = new ConstraintSet();
            holder.txtMessage.setBackgroundResource(R.drawable.r_message_bg);
            Glide.with(context).load(recipientImg).error(R.drawable.default_profile).placeholder(R.drawable.default_profile).into(holder.profileImage);
            constraintSet.clone(constraintLayout); // clones the main cc layout(contains the message and the profile image)
            constraintSet.clear(R.id.profileCardView,ConstraintSet.RIGHT);
            constraintSet.clear(R.id.txtMessage,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.profileCardView,ConstraintSet.LEFT,R.id.ccLayout,ConstraintSet.LEFT,0);
            constraintSet.connect(R.id.txtMessage,ConstraintSet.LEFT,R.id.profileCardView,ConstraintSet.RIGHT,0);
            constraintSet.connect(R.id.txtDateTime,ConstraintSet.LEFT,R.id.profileCardView,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        ConstraintLayout ccLayout;
        TextView txtMessage, txtDateTime;
        ImageView profileImage;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            ccLayout = itemView.findViewById(R.id.ccLayout);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            profileImage = itemView.findViewById(R.id.imgRecipientImage);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);

        }
    }
}
