package com.example.chatapp3;

import androidx.annotation.NonNull;
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

public class LoginActivity extends AppCompatActivity {
    private EditText lemail , lpassword, username;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lemail = findViewById(R.id.email);
        lpassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

    }
    public void onClickSignIn(View view)
    {
        if(!TextUtils.isEmpty(lemail.getText().toString()) && !TextUtils.isEmpty(lpassword.getText().toString())){
            mAuth.signInWithEmailAndPassword(lemail.getText().toString(),lpassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User SignIn failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please entre Email and Password", Toast.LENGTH_SHORT).show();
        }
    }
    public void BackBtn(View view){
        Intent i = new Intent(LoginActivity.this, AuthActivity.class);

        startActivity(i);
        finish();
    }
}