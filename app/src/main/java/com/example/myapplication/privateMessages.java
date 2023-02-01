package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class privateMessages extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editMessageText;
    private TextView txtRecipientName;
    private ProgressBar progressBar;
    private ImageView recipientImage, imgSend;
    private ArrayList<Message> messages;

    private MessageAdapter messageAdapter;
    String recipientName, recipientEmail, chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_messages);

        recipientName = getIntent().getStringExtra("recipient_name");
        recipientEmail = getIntent().getStringExtra("recipient_email");
//        Toast.makeText(this, recipientEmail, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerMessages);
        editMessageText = findViewById(R.id.editMessage);
        txtRecipientName = findViewById(R.id.txtRecipientName);
        progressBar = findViewById(R.id.progressBar);
        recipientImage = findViewById(R.id.imgRecipientImage);
        imgSend = findViewById(R.id.imgSendMessage);

        txtRecipientName.setText(recipientName);

        messages = new ArrayList<>();

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("messages/" + chatId).push().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),recipientEmail,editMessageText.getText().toString()));
                editMessageText.setText("");
            }

        });

        messageAdapter = new MessageAdapter(messages,getIntent().getStringExtra("sender_img"),getIntent().getStringExtra("recipient_img"),privateMessages.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        Glide.with(privateMessages.this).load(getIntent().getStringExtra("recipient_img")).placeholder(R.drawable.default_profile).error(R.drawable.default_profile).into(recipientImage);

        createChatRoom();

    }

    private void createChatRoom() {
        FirebaseDatabase.getInstance().getReference("User/" + FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentUserName = snapshot.getValue(User.class).getUsername();
                if(recipientName.compareTo(currentUserName) > 0) {
                    chatId = currentUserName + recipientName;
                } else if(recipientName.compareTo(currentUserName) == 0) {
                    chatId = currentUserName + recipientName;
                } else {
                    chatId = recipientName + currentUserName;
                }
                attachMessageListener(chatId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attachMessageListener(String chatId) {
        FirebaseDatabase.getInstance().getReference("messages/" + chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size() - 1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
