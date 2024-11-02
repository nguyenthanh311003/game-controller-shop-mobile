package com.group4.gamecontrollershop.model;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.gamecontrollershop.R;
import com.group4.gamecontrollershop.adapter.ChatAdapter;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etMessage;
    private Button btnSend;
    private ChatAdapter chatAdapter;
    private DatabaseHelper db;
    private List<Message> messageList;
    private int userId;
    private int otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = new DatabaseHelper(this);

        // Assuming you pass otherUserId and userId via Intent
        userId = getIntent().getIntExtra("userId", -1);
        otherUserId = 1;

        recyclerView = findViewById(R.id.recyclerView);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        messageList = db.getMessages(userId, otherUserId);
        chatAdapter = new ChatAdapter(messageList, userId);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSend.setOnClickListener(v -> {
            String messageText = etMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                db.insertMessage(userId, otherUserId, messageText);
                messageList.add(new Message(0, userId, otherUserId, messageText, null));
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                etMessage.setText("");
                recyclerView.scrollToPosition(messageList.size() - 1);
            }
        });
    }
}
