package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SelectPlayerActivity extends AppCompatActivity {
    String player;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);

        header = findViewById(R.id.txtSelectPlayer);
        Bundle bundle = getIntent().getExtras();
        player = bundle.getString("player");
        header.setText("Select Player " + player);
    }
}