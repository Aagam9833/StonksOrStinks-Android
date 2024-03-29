package com.example.stonksorstinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingLobby extends AppCompatActivity {

    TextView joinedPlayersTextView, sharingID;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    String roomID;
    int maxPlayers;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_lobby);
        Intent intent = getIntent();
        roomID = intent.getStringExtra("ROOMID");
        username = intent.getStringExtra("USERNAME");
        joinedPlayersTextView = findViewById(R.id.players_joined);
        sharingID = findViewById(R.id.room_id_share);
        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

        databaseReference.child(roomID).child("maxPlayers").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                maxPlayers = Integer.parseInt(dataSnapshot.getValue().toString());
                postMaxPlayers();
            }
        });

    }

    private void postMaxPlayers() {
        valueEventListener = databaseReference.child(roomID).child("noOfPlayers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals(String.valueOf(maxPlayers))) {
                    databaseReference.child(roomID).child("full").setValue(true);
                    databaseReference.child(roomID).child("locked").setValue(true);
                    databaseReference.child(roomID).removeEventListener(valueEventListener);
                    Intent newIntent = new Intent(WaitingLobby.this, GamePage.class);
                    newIntent.putExtra("ROOMID", roomID);
                    newIntent.putExtra("USERNAME", username);
                    startActivity(newIntent);
                    finish();
                } else {
                    databaseReference.child(roomID).child("maxPlayers").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            maxPlayers = Integer.parseInt(dataSnapshot.getValue().toString());
                            sharingID.setText("Share Room ID with friends: " + roomID);
                            joinedPlayersTextView.setText(snapshot.getValue().toString() + "/" + String.valueOf(maxPlayers));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child(roomID).child("noOfPlayers").removeEventListener(valueEventListener);
    }
}