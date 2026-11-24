package com.example.villageconnect;

import android.content.Context;
import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<ChatMessage> list;

    public ChatAdapter(Context context, ArrayList<ChatMessage> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position){
        return list.get(position).isUser() ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){

        if(viewType==1){

            View view=LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_user,parent,false);

            return new UserHolder(view);

        }else{

            View view=LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_ai,parent,false);

            return new AIHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position){

        ChatMessage msg=list.get(position);

        if(holder instanceof UserHolder){

            ((UserHolder) holder).txt.setText(msg.getMessage());

        }else{

            ((AIHolder) holder).txt.setText(msg.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{

        TextView txt;

        public UserHolder(View itemView){
            super(itemView);
            txt=itemView.findViewById(R.id.txtUser);
        }
    }

    class AIHolder extends RecyclerView.ViewHolder{

        TextView txt;

        public AIHolder(View itemView){
            super(itemView);
            txt=itemView.findViewById(R.id.txtAI);
        }
    }
}