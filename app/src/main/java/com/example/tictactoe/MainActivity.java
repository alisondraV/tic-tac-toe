package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int NUMBER_OF_CLICKS_TO_DRAW = 9;
    public static final String DRAW = "DRAW";
    Button newGameButton;
    TextView playerOneTextView, playerTwoTextView;
    Button[][] ticTacToeButtons = new Button[3][3];

    private SharedPreferences savedValues;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Player playerOne;
    Player playerTwo;
    private long playerOneId;
    private long playerTwoId;

    private int cellsClickedInCurrentGame = 0;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Player");

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
        playerOneId = savedValues.getLong("player1Id", 0);
        playerTwoId = savedValues.getLong("player2Id", 0);

        databaseReference.child(String.valueOf(playerOneId)).get().addOnCompleteListener(task -> {
            playerOne = Objects.requireNonNull(task.getResult()).getValue(Player.class);
            assert playerOne != null;
            playerOneTextView.setText(playerOne.getName());
        });

        databaseReference.child(String.valueOf(playerTwoId)).get().addOnCompleteListener(task -> {
            playerTwo = Objects.requireNonNull(task.getResult()).getValue(Player.class);
            assert playerTwo != null;
            playerTwoTextView.setText(playerTwo.getName());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        playerOneTextView.setText(savedValues.getString("player1Name", "empty"));
        playerTwoTextView.setText(savedValues.getString("player2Name", "empty"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.reset_game) {
            resetGame();
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
                String winner = firstValue.equals("X") ? playerOne.getName() : playerTwo.getName();
                Toast.makeText(this, winner + " has won!", Toast.LENGTH_SHORT).show();

                updatePlayersScores(winner);
                stopGame();
            }
        }

        cellsClickedInCurrentGame++;
        if (cellsClickedInCurrentGame == NUMBER_OF_CLICKS_TO_DRAW) {
            Toast.makeText(this, "Game Draw!!", Toast.LENGTH_SHORT).show();
            updatePlayersScores(DRAW);
            stopGame();
        }
    }

    private void updatePlayersScores(String winner) {
        if (winner.equals(playerOne.getName())) {
            playerOne.increaseWins();
            playerTwo.increaseLosses();
        } else if (winner.equals(playerTwo.getName())) {
            playerTwo.increaseWins();
            playerOne.increaseLosses();
        } else if (winner.equals(DRAW)) {
            playerOne.increaseTies();
            playerTwo.increaseTies();
        }

        databaseReference.child(String.valueOf(playerOneId)).setValue(playerOne);
        databaseReference.child(String.valueOf(playerTwoId)).setValue(playerTwo);
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

    private void resetGame() {
        stopGame();
        newGame();
    }

    private boolean isButtonEligibleToBeMarked(View view, Button button) {
        return view.getId() == button.getId() && button.getText() == "";
    }

    private int charToInt(char c) {
        return Character.getNumericValue(c);
    }
}