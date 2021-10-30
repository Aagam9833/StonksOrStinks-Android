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

import com.example.stonksorstinks.Utils.BottomModalSheetCreateRoom;
import com.example.stonksorstinks.Utils.BottomModalSheetJoinRoom;
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
    DatabaseReference databaseReference;
    BottomModalSheetCreateRoom bottomModalSheetCreateRoom;
    BottomModalSheetJoinRoom bottomModalSheetJoinRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_lobby);

        Intent previousIntent = getIntent();
        String username = previousIntent.getStringExtra("USERNAME");

        joinBtn = findViewById(R.id.join_game_button);
        createBtn = findViewById(R.id.create_game_button);

        bottomModalSheetCreateRoom = new BottomModalSheetCreateRoom(username);
        bottomModalSheetJoinRoom = new BottomModalSheetJoinRoom(username);

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

        joinBtn.setOnClickListener(view -> {
            bottomModalSheetJoinRoom.show(getSupportFragmentManager(), "JoinBottomModalSheet");
        });

        createBtn.setOnClickListener(view -> {
            bottomModalSheetCreateRoom.show(getSupportFragmentManager(), "CreateBottomModalSheet");
        });

    }
}