package com.example.chatapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;


    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    String spaceSender;
    String spaceReceiver;

    public DataAdapter(Context context, ArrayList<Message> messages,  String spaceSender, String spaceReceiver) {
        this.context = context;

        this.messages = messages;
        this.spaceSender = spaceSender;
        this.spaceReceiver = spaceReceiver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);







        if(holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder)holder;



            viewHolder.message.setText(message.getMsg());


        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            viewHolder.messageR.setText(message.getMsg());


    }}

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView messageR;
        TextView nameR;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            messageR = itemView.findViewById(R.id.messageR);

        }
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

       TextView message;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }



}