package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SelectPlayerActivity extends AppCompatActivity {
    String player;
    TextView header;
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Player> arrayList = new ArrayList<>();
    ArrayAdapter<Player> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);

        header = findViewById(R.id.txtSelectPlayer);
        listView = findViewById(R.id.listViewSelectPlayer);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Player");

        Bundle bundle = getIntent().getExtras();
        player = bundle.getString("player");
        header.setText("Select Player " + player);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Player newPlayer = snapshot.getValue(Player.class);
                arrayList.add(newPlayer);
                arrayAdapter = new ArrayAdapter<>(SelectPlayerActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Player playerModel = (Player) adapterView.getItemAtPosition(i);

                    Intent intent = new Intent(SelectPlayerActivity.this, MainMenuActivity.class);

                    intent.putExtra("player" + player + "Id", playerModel.getId());
                    intent.putExtra("player" + player + "Name", playerModel.getName());
                    startActivity(intent);
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}