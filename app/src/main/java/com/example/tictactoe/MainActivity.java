package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button newGameButton;
    Button[][] ticTacToeButtons = new Button[3][3];
    private boolean xTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.btnNewGame);
        ticTacToeButtons[0][0] = findViewById(R.id.btn00);
        ticTacToeButtons[0][1] = findViewById(R.id.btn01);
        ticTacToeButtons[0][2] = findViewById(R.id.btn02);
        ticTacToeButtons[1][0] = findViewById(R.id.btn10);
        ticTacToeButtons[1][1] = findViewById(R.id.btn11);
        ticTacToeButtons[1][2] = findViewById(R.id.btn12);
        ticTacToeButtons[2][0] = findViewById(R.id.btn20);
        ticTacToeButtons[2][1] = findViewById(R.id.btn21);
        ticTacToeButtons[2][2] = findViewById(R.id.btn22);

        newGameButton.setOnClickListener(this);
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                ticTacToeButtons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.scores) {
            startActivity(new Intent(this, ScoresActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNewGame) {
            newGame();
            return;
        }

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                Button currentButton = ticTacToeButtons[i][j];
                if (isButtonEligibleToBeMarked(view, currentButton)) {
                    currentButton.setText(xTurn ? "X" : "O");
                    xTurn = !xTurn;
                }
            }
        }
    }

    private boolean isButtonEligibleToBeMarked(View view, Button button) {
        return view.getId() == button.getId() && button.getText() == "";
    }

    private void newGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToeButtons[i][j].setText("");
            }
        }
    }
}