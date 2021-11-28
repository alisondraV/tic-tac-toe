package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPlayerActivity extends AppCompatActivity {
    EditText playerName;
    Button addPlayer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Player player;
    long maxId = 0;
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Toast.makeText(AddPlayerActivity.this, "Player has been successfully created!", Toast.LENGTH_SHORT).show();
            playerName.setText("");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(AddPlayerActivity.this, "An error occurred while creating a player :(", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        playerName = findViewById(R.id.etPlayerName);
        addPlayer = findViewById(R.id.btnAddPlayerName);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Player");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxId = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        addPlayer.setOnClickListener(view -> {
            String name = String.valueOf(playerName.getText());

            if (name.equals("")) {
                Toast.makeText(AddPlayerActivity.this, "You should specify a player name", Toast.LENGTH_SHORT).show();
            } else {
                player = new Player(maxId + 1, name, 0, 0, 0);
                databaseReference.child(String.valueOf(player.getId())).setValue(player);
            }

            databaseReference.addValueEventListener(valueEventListener);
        });
    }
}