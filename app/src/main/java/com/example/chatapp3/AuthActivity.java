package com.example.chatapp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AuthActivity extends AppCompatActivity {
    private EditText email , password, username;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        username = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null)
        { Intent i = new Intent(AuthActivity.this, MainActivity.class);

            startActivity(i);
            finish();
            }



        else
        {
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view)
    { String usern = username.getText().toString().trim();
        String emaile = email.getText().toString().trim();
        String uid = mAuth.getUid();
        if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()))
        {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        User user = new User(emaile,usern, uid );

                        FirebaseDatabase.getInstance().getReference()
                                .child("users")
                                .child("uid")
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Intent i = new Intent(AuthActivity.this, MainActivity.class);

                                    startActivity(i);
                                    finish();}
                                else { Toast.makeText(getApplicationContext(), "User SignUp failed Name", Toast.LENGTH_SHORT).show();}
                            }

                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User SignUp failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please entre Email and Password", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickSign(View view){
        Intent i = new Intent(AuthActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}