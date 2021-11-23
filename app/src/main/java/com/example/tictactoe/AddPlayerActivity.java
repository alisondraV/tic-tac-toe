package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddPlayerActivity extends AppCompatActivity {
    EditText playerName;
    Button addPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        playerName = findViewById(R.id.etPlayerName);
        addPlayer = findViewById(R.id.btnAddPlayerName);

        addPlayer.setOnClickListener(view -> {
            String name = String.valueOf(playerName.getText());
        });
    }
}