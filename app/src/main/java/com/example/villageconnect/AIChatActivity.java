package com.example.villageconnect;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AIChatActivity extends AppCompatActivity {

    private RecyclerView recyclerChat;
    private TextInputEditText etMessage;
    private ImageButton btnSend;

    private ArrayList<ChatMessage> chatList;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Views
        recyclerChat = findViewById(R.id.recyclerChat);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // RecyclerView
        chatList = new ArrayList<>();
        adapter = new ChatAdapter(this, chatList);

        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerChat.setAdapter(adapter);

        // Welcome message
        chatList.add(new ChatMessage(
                "👋 Hello! I'm Village Connect AI.\nAsk me about government schemes.",
                false));
        adapter.notifyItemInserted(0);

        // Send Button
        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {

        String user = etMessage.getText().toString().trim();

        if (user.isEmpty()) return;

        // User bubble
        chatList.add(new ChatMessage(user, true));
        adapter.notifyItemInserted(chatList.size() - 1);
        recyclerChat.scrollToPosition(chatList.size() - 1);

        etMessage.setText("");

        String prompt =
                "You are Village Connect AI. " +
                        "Help rural citizens with government welfare schemes. " +
                        "Reply in simple English. " +
                        "Question: " + user;

        ApiClient.askGemini(prompt, new ApiClient.GeminiCallback() {

            @Override
            public void onResponse(String text) {
                runOnUiThread(() -> {
                    chatList.add(new ChatMessage(text, false));
                    adapter.notifyItemInserted(chatList.size() - 1);
                    recyclerChat.scrollToPosition(chatList.size() - 1);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    chatList.add(new ChatMessage(
                            "❌ " + error, false));
                    adapter.notifyItemInserted(chatList.size() - 1);
                    recyclerChat.scrollToPosition(chatList.size() - 1);
                });
            }
        });
    }
}