package com.example.villageconnect;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private LinearLayout notificationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationContainer = findViewById(R.id.notificationContainer);

        loadNotifications();
    }

    private void loadNotifications() {

        notificationContainer.removeAllViews();

        ArrayList<String> notifications =
                NotificationHelper.getNotifications(this);

        if (notifications.isEmpty()) {

            TextView emptyText = new TextView(this);

            emptyText.setText("No notifications yet 🔔");
            emptyText.setTextSize(16);
            emptyText.setPadding(32, 32, 32, 32);

            notificationContainer.addView(emptyText);
            return;
        }

        for (String notification : notifications) {

            String[] parts = notification.split("\\|\\|", 2);

            String title = parts.length > 0 ? parts[0] : "Notification";
            String message = parts.length > 1 ? parts[1] : "";

            TextView notificationView = new TextView(this);

            notificationView.setText(
                    "🔔 " + title + "\n\n" + message
            );

            notificationView.setTextSize(16);
            notificationView.setPadding(32, 28, 32, 28);

            notificationContainer.addView(notificationView);
        }
    }
}