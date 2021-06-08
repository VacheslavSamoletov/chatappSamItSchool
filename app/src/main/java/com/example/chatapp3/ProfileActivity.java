package com.example.chatapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);

    bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.chats:

                    Intent i = new Intent(ProfileActivity.this, MenuActivity.class);

                    startActivity(i);

                    break;
            }
            return false;
        }

    });
}
    public void SignOut(View view){
        FirebaseAuth.getInstance().signOut();

        Intent i = new Intent(ProfileActivity.this, AuthActivity.class);

        startActivity(i);

    }}