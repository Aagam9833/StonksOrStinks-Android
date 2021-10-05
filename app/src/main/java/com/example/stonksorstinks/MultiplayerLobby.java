package com.example.stonksorstinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.stonksorstinks.models.Player;
import com.example.stonksorstinks.models.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class MultiplayerLobby extends AppCompatActivity {

    Button joinBtn, createBtn;
    EditText joinEditText;
    ProgressBar progressBar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_lobby);

        Intent previousIntent = getIntent();
        String username = previousIntent.getStringExtra("USERNAME");

        joinBtn = findViewById(R.id.join_game_button);
        createBtn = findViewById(R.id.create_game_button);
        joinEditText = findViewById(R.id.join_game_code);
        progressBar = findViewById(R.id.multiplayer_progress_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

        joinBtn.setOnClickListener(view -> {
            String roomID = joinEditText.getText().toString().trim();
            if (roomID.equals("")) {
                Toast.makeText(this, "Enter a valid room ID", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(roomID)) {
                            int noOfPlayers = Integer.parseInt(dataSnapshot.child(roomID).child("noOfPlayers").getValue().toString());
                            int maxPlayers = Integer.parseInt(dataSnapshot.child(roomID).child("maxPlayers").getValue().toString());
                            Boolean isFull = (Boolean) dataSnapshot.child(roomID).child("full").getValue();
                            if (!isFull) {
                                if (noOfPlayers < maxPlayers) {
                                    Player player = new Player(username, false);
                                    Map<String, Player> playerMap = (Map<String, Player>) dataSnapshot.child(roomID).child("players").getValue();
                                    playerMap.put(username,player);
                                    noOfPlayers++;
                                    databaseReference.child(roomID).child("noOfPlayers").setValue(noOfPlayers);
                                    databaseReference.child(roomID).child("players").setValue(playerMap);
                                    Intent intent = new Intent(MultiplayerLobby.this,WaitingLobby.class);
                                    intent.putExtra("ROOMID",roomID);
                                    intent.putExtra("USERNAME",username);
                                    startActivity(intent);
                                } else if (noOfPlayers == maxPlayers) {
                                    Player player = new Player(username, false);
                                    Map<String, Player> playerMap = (Map<String, Player>) dataSnapshot.child(roomID).child("players").getValue();
                                    playerMap.put(username,player);
                                    noOfPlayers++;
                                    isFull = true;
                                    databaseReference.child(roomID).child("noOfPlayers").setValue(noOfPlayers);
                                    databaseReference.child(roomID).child("players").setValue(playerMap);
                                    databaseReference.child(roomID).child("full").setValue(isFull);
                                    Intent intent = new Intent(MultiplayerLobby.this,WaitingLobby.class);
                                    intent.putExtra("ROOMID",roomID);
                                    intent.putExtra("USERNAME",username);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(MultiplayerLobby.this, "Room is full", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MultiplayerLobby.this, "Room does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        createBtn.setOnClickListener(view -> {
            if (username.equals("")) {
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
            } else {
                createBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                int randomNumber = (int) (Math.random() * (999999 - 100000) + 100000);
                Player newPlayer = new Player(username, true);
                Map<String, Player> playerMap = new HashMap<String, Player>();
                playerMap.put(username,newPlayer);
                Room newRoom = new Room(randomNumber, playerMap, false, false);
                databaseReference.child(String.valueOf(randomNumber))
                        .setValue(newRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MultiplayerLobby.this, WaitingLobby.class);
                            intent.putExtra("ROOMID", String.valueOf(randomNumber));
                            intent.putExtra("USERNAME", username);
                            startActivity(intent);
                        } else {
                            createBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Log.d("TAGGED", task.getException().toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Log.d("TAGGED", e.toString());
                    }
                });
            }

        });

    }
}