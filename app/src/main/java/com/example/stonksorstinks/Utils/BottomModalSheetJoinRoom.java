package com.example.stonksorstinks.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stonksorstinks.WaitingLobby;
import com.example.stonksorstinks.databinding.JoinModalSheetBinding;
import com.example.stonksorstinks.models.Player;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class BottomModalSheetJoinRoom extends BottomSheetDialogFragment {

    JoinModalSheetBinding binding;
    DatabaseReference databaseReference;
    String USERNAME;

    public BottomModalSheetJoinRoom(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = JoinModalSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

        binding.joinGameButton.setOnClickListener(view1 -> {
            String roomID = binding.joinGameCode.getText().toString().trim();

            if (roomID.equals("")) {
                Toast.makeText(getContext(), "Enter A Valid Room ID", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.hasChild(roomID)) {
                        int noOfPlayers = Integer.parseInt(dataSnapshot.child(roomID).child("noOfPlayers").getValue().toString());
                        int maxPlayers = Integer.parseInt(dataSnapshot.child(roomID).child("maxPlayers").getValue().toString());
                        Boolean isFull = (Boolean) dataSnapshot.child(roomID).child("full").getValue();
                        if (!isFull) {
                            if (noOfPlayers < maxPlayers) {
                                Player player = new Player(USERNAME, false);
                                Map<String, Player> playerMap = (Map<String, Player>) dataSnapshot.child(roomID).child("players").getValue();
                                playerMap.put(USERNAME, player);
                                noOfPlayers++;
                                databaseReference.child(roomID).child("noOfPlayers").setValue(noOfPlayers);
                                databaseReference.child(roomID).child("players").setValue(playerMap);
                                Intent intent = new Intent(getActivity(), WaitingLobby.class);
                                intent.putExtra("ROOMID", roomID);
                                intent.putExtra("USERNAME", USERNAME);
                                startActivity(intent);
                            } else if (noOfPlayers == maxPlayers) {
                                Player player = new Player(USERNAME, false);
                                Map<String, Player> playerMap = (Map<String, Player>) dataSnapshot.child(roomID).child("players").getValue();
                                playerMap.put(USERNAME, player);
                                noOfPlayers++;
                                isFull = true;
                                databaseReference.child(roomID).child("noOfPlayers").setValue(noOfPlayers);
                                databaseReference.child(roomID).child("players").setValue(playerMap);
                                databaseReference.child(roomID).child("full").setValue(isFull);
                                Intent intent = new Intent(getActivity(), WaitingLobby.class);
                                intent.putExtra("ROOMID", roomID);
                                intent.putExtra("USERNAME", USERNAME);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getContext(), "Room Is Full", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Room Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}
