package com.example.villageconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    Context context;
    ArrayList<DashboardItem> list;

    public DashboardAdapter(Context context, ArrayList<DashboardItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.dashboard_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DashboardItem item = list.get(position);

        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.image.setImageResource(item.getImage());

        holder.card.setOnClickListener(v -> {

            Intent intent = null;

            switch (item.getTitle()) {

                case "AI Assistant":
                    intent = new Intent(context, AIChatActivity.class);
                    break;

                case "Find Schemes":
                    intent = new Intent(context, SchemeActivity.class);
                    break;

                case "OCR Scanner":
                    intent = new Intent(context, OCRActivity.class);
                    break;

                case "Voice Search":
                    intent = new Intent(context, VoiceAssistantActivity.class);
                    break;

                case "Nearby Offices":
                    intent = new Intent(context, MapsActivity.class);
                    break;

                case "Profile":
                    intent = new Intent(context, ProfileActivity.class);
                    break;

                case "Track Application":
                    intent = new Intent(context, ApplicationTrackingActivity.class);
                    break;

                case "Documents":
                    intent = new Intent(context, OCRActivity.class);
                    break;

                default:
                    Toast.makeText(context,
                            item.getTitle(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }

            if (intent != null) {
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, subtitle;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgIcon);
            title = itemView.findViewById(R.id.txtTitle);
            subtitle = itemView.findViewById(R.id.txtSub);
            card = itemView.findViewById(R.id.cardDashboard);
        }
    }
}