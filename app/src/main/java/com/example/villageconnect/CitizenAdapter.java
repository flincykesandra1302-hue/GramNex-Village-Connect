package com.example.villageconnect;

import android.content.Context;
import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitizenAdapter extends RecyclerView.Adapter<CitizenAdapter.ViewHolder>{

    Context context;
    ArrayList<Citizen> list;

    public CitizenAdapter(Context context, ArrayList<Citizen> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(context)
                .inflate(R.layout.citizen_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        Citizen c = list.get(position);

        holder.txtName.setText(c.getName());
        holder.txtPhone.setText(c.getPhone());
        holder.txtOccupation.setText(c.getOccupation());
        holder.txtDistrict.setText(c.getDistrict());
        holder.itemView.findViewById(R.id.btnViewProfile)
                .setOnClickListener(v -> {

                    android.content.Intent intent =
                            new android.content.Intent(
                                    context,
                                    VolunteerAssistanceActivity.class
                            );

                    intent.putExtra(
                            "citizenName",
                            c.getName()
                    );

                    context.startActivity(intent);
                });

    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName,txtPhone,txtOccupation,txtDistrict;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtOccupation = itemView.findViewById(R.id.txtOccupation);
            txtDistrict = itemView.findViewById(R.id.txtDistrict);
        }
    }
}