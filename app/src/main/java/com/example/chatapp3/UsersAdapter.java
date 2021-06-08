package com.example.chatapp3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.GetUsersViewHolder> {

    Context context;
   private ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public GetUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stroke_users, parent, false);

        return new  GetUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  GetUsersViewHolder holder, int position) {
        User user = users.get(position);

        String IDsender = FirebaseAuth.getInstance().getUid();

        String spaceSender  = IDsender + user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(spaceSender)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snap) {
                        if(snap.exists()) {
                            String lastMsg = snap.child("lastMsg").getValue(String.class);
                            long time = snap.child("lastMsgTime").getValue(Long.class);
                            SimpleDateFormat  dateFormat = new SimpleDateFormat("d MMM yyyy",
                                    Locale.getDefault());
                            holder.msgTime.setText(dateFormat.format(new Date(time)));
                            holder.lastMsgIcon.setText(lastMsg);
                        } else {
                            holder.lastMsgIcon.setText("Tap to chat");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.usernameIcon.setText(user.getUsername());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class  GetUsersViewHolder extends RecyclerView.ViewHolder {

        TextView usernameIcon;
        TextView lastMsgIcon;
        TextView msgTime;

        public GetUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameIcon = itemView.findViewById(R.id.usernameIcon);
            lastMsgIcon = itemView.findViewById(R.id.lastMsgIcon);
            msgTime = itemView.findViewById(R.id.msgTime);

        }
    }

}
