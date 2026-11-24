package com.example.villageconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder> {

    Context context;
    ArrayList<String> schemeList;
    ArrayList<String> fullList;

    public SchemeAdapter(Context context, ArrayList<String> schemeList) {
        this.context = context;
        this.schemeList = new ArrayList<>(schemeList);
        this.fullList = new ArrayList<>(schemeList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.scheme_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSchemeName.setText(schemeList.get(position));
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    public void filter(String text) {

        schemeList.clear();

        if (text.isEmpty()) {

            schemeList.addAll(fullList);

        } else {

            for (String item : fullList) {

                if (item.toLowerCase().contains(text.toLowerCase())) {
                    schemeList.add(item);
                }

            }

        }

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSchemeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSchemeName = itemView.findViewById(R.id.txtSchemeName);
        }
    }
}