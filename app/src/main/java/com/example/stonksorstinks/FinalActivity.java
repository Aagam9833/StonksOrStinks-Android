package com.example.stonksorstinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinalActivity extends AppCompatActivity {

    TextView winnerView,winnerNet;
    Button returnToMainMenu;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        winnerView = findViewById(R.id.winner_username);
        winnerNet = findViewById(R.id.winner_networth);
        returnToMainMenu = findViewById(R.id.start_new_game);

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

        Intent previousIntent = getIntent();
        int netWorth = previousIntent.getIntExtra("WINNER",0);
        String winner = previousIntent.getStringExtra("WINNERNAME");
        String roomID = previousIntent.getStringExtra("ROOMID");
        String username = previousIntent.getStringExtra("USERNAME");

        winnerView.setText(winner);
        winnerNet.setText("Networth: " + netWorth);

        returnToMainMenu.setOnClickListener(view -> {
            databaseReference.child(roomID).child("players")
                    .child(username).child("host").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    boolean isHost = (boolean) dataSnapshot.getValue();
                    if(isHost){
                        databaseReference.child(roomID).removeValue();
                    }
                }
            });
            startActivity(new Intent(this,MainMenu.class));
            finish();
        });

    }

    @Override
    public void onBackPressed() {

    }
}