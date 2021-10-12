package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int NUMBER_OF_CLICKS_TO_DRAW = 9;
    Button newGameButton;
    TextView playerOneTextView;
    TextView playerTwoTextView;
    Button[][] ticTacToeButtons = new Button[3][3];
    private SharedPreferences savedValues;

    private int cellsClickedInCurrentGame = 0;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private boolean xTurn = true;
    String[] winningCombinations = {
            "000102",
            "101112",
            "202122",
            "001020",
            "011121",
            "021222",
            "001122",
            "021120"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.btnNewGame);
        playerOneTextView = findViewById(R.id.txtPlayerOne);
        playerTwoTextView = findViewById(R.id.txtPlayerTwo);
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

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        Editor editor = savedValues.edit();
        editor.putInt("playerOneScore", playerOneScore);
        editor.putInt("playerTwoScore", playerTwoScore);
        editor.apply();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        playerOneScore = savedValues.getInt("playerOneScore", 0);
        playerTwoScore = savedValues.getInt("playerTwoScore", 0);
        playerOneTextView.setText(playerOneScore + "");
        playerTwoTextView.setText(playerTwoScore + "");
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
        checkGame();
    }

    private void newGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToeButtons[i][j].setText("");
                ticTacToeButtons[i][j].setEnabled(true);
            }
        }
    }

    private void checkGame() {
        for (String c: winningCombinations) {
            String firstValue = (String) ticTacToeButtons[charToInt(c.charAt(0))][charToInt(c.charAt(1))].getText();
            String secondValue = (String) ticTacToeButtons[charToInt(c.charAt(2))][charToInt(c.charAt(3))].getText();
            String thirdValue = (String) ticTacToeButtons[charToInt(c.charAt(4))][charToInt(c.charAt(5))].getText();

            if (firstValue.equals(secondValue) && secondValue.equals(thirdValue) && !thirdValue.equals("")) {
                if (firstValue.equals("X")) {
                    playerOneScore++;
                    playerOneTextView.setText(playerOneScore + "");
                    Toast.makeText(this, "Player One has won!", Toast.LENGTH_SHORT).show();
                } else {
                    playerTwoScore++;
                    playerTwoTextView.setText(playerTwoScore + "");
                    Toast.makeText(this, "Player Two has won!", Toast.LENGTH_SHORT).show();
                }
                stopGame();
            }
        }

        cellsClickedInCurrentGame++;
        if (cellsClickedInCurrentGame == NUMBER_OF_CLICKS_TO_DRAW) {
            Toast.makeText(this, "Game Draw!!", Toast.LENGTH_SHORT).show();
            stopGame();
        }
    }

    private void stopGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToeButtons[i][j].setEnabled(false);
            }
        }
        xTurn = true;
        cellsClickedInCurrentGame = 0;
    }

    private boolean isButtonEligibleToBeMarked(View view, Button button) {
        return view.getId() == button.getId() && button.getText() == "";
    }

    private int charToInt(char c) {
        return Character.getNumericValue(c);
    }
}