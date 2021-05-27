package com.example.chatapp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("messages");
    EditText messageField;
    private FirebaseAuth mAuth;

    RecyclerView list_of_messages;

    TextView message_user;
    String senderUid;
    String receiverUid;
    ArrayList<Message> messages;
    ArrayList<User> users;
    String spaceSender, spaceReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = getIntent().getStringExtra("name");
        String uid = getIntent().getStringExtra("uid");


        FloatingActionButton btn =
                (FloatingActionButton) findViewById(R.id.btnSend);
        messages = new ArrayList<>();
        users = new ArrayList<>();
        messageField = findViewById(R.id.messageField);
        list_of_messages = findViewById(R.id.list_of_messages);

        
        DatabaseReference myRef = database.getReference("chats");


        spaceSender = senderUid + receiverUid;
       spaceReceiver = receiverUid + senderUid;

        senderUid = FirebaseAuth.getInstance().getUid();
        receiverUid = FirebaseAuth.getInstance().getUid();
        DataAdapter dataAdapter = new DataAdapter(this, messages,  spaceSender, spaceReceiver);




        LinearLayoutManager lin = (new LinearLayoutManager(this));
        lin.setStackFromEnd(true);
        list_of_messages.setLayoutManager(lin);

        list_of_messages.setAdapter(dataAdapter);
        TextView item_receive = findViewById(R.id.messageR);






        database.getReference().child("chats")
                .child(spaceSender)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Message message = snapshot1.getValue(Message.class);
                            messages.add(message);
                        }

                        dataAdapter.notifyDataSetChanged();
                        list_of_messages.smoothScrollToPosition(messages.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt = messageField.getText().toString();

                Date date = new Date();
                Message messages = new Message(messageTxt, senderUid, date.getTime());
                messageField.setText("");

                String randomKey = database.getReference().push().getKey();


                database.getReference().child("chats")
                        .child(spaceSender)
                        .child("messages")
                        .child(randomKey)
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(spaceReceiver)
                                .child("messages")
                                .child(randomKey)
                                .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });

            }
        });






    }


    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }




}