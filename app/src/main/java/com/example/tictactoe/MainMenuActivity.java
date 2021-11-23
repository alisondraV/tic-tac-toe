package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity implements OnClickListener {
    Button startGame;
    Button scoreboard;
    Button selectPlayer1;
    Button selectPlayer2;
    Button addPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startGame = findViewById(R.id.btn_start);
        scoreboard = findViewById(R.id.btn_scoreboard);
        selectPlayer1 = findViewById(R.id.btn_player1);
        selectPlayer2 = findViewById(R.id.btn_player2);
        addPlayer = findViewById(R.id.btn_add_player);

        startGame.setOnClickListener(this);
        scoreboard.setOnClickListener(this);
        selectPlayer1.setOnClickListener(this);
        selectPlayer2.setOnClickListener(this);
        addPlayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent gameActivity = new Intent(this, MainActivity.class);
                this.startActivity(gameActivity);
                break;
            case R.id.btn_scoreboard:
                Intent scoreboardActivity = new Intent(this, ScoreboardActivity.class);
                this.startActivity(scoreboardActivity);
                break;
            case R.id.btn_player1:
                Intent selectPlayer1 = new Intent(this, SelectPlayerActivity.class);
                selectPlayer1.putExtra("player", "1");
                this.startActivity(selectPlayer1);
                break;
            case R.id.btn_player2:
                Intent selectPlayer2 = new Intent(this, SelectPlayerActivity.class);
                selectPlayer2.putExtra("player", "2");
                this.startActivity(selectPlayer2);
                break;
            case R.id.btn_add_player:
                Intent addPlayerActivity = new Intent(this, AddPlayerActivity.class);
                this.startActivity(addPlayerActivity);
                break;
        }
    }
}