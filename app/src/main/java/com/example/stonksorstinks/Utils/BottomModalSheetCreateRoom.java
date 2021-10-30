package com.example.stonksorstinks.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stonksorstinks.WaitingLobby;
import com.example.stonksorstinks.databinding.CreateModalSheetBinding;
import com.example.stonksorstinks.models.Player;
import com.example.stonksorstinks.models.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BottomModalSheetCreateRoom extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {

    CreateModalSheetBinding binding;
    boolean isLock;
    int maxPlayers;
    RadioButton radioButton;
    String[] maxPlayersSpinner = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String USERNAME;
    DatabaseReference databaseReference;

    public BottomModalSheetCreateRoom(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CreateModalSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.maxPlayersSpinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, maxPlayersSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.maxPlayersSpinner.setAdapter(arrayAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

        binding.createRoomButton.setOnClickListener(view1 -> {

            int SelectedOption = binding.lockRoomRadio.getCheckedRadioButtonId();
            if (SelectedOption == -1) {
                Toast.makeText(getContext(), "Please Select To Lock Room", Toast.LENGTH_SHORT).show();
            } else {
                binding.createRoomButton.setVisibility(View.GONE);
                binding.multiplayerProgressBar.setVisibility(View.VISIBLE);
                radioButton = binding.lockRoomRadio.findViewById(SelectedOption);
                if (radioButton.getText().equals("Yes")) {
                    isLock = true;
                } else if (radioButton.getText().equals("No")) {
                    isLock = false;
                }
                int randomNumber = (int) (Math.random() * (999999 - 100000) + 100000);
                Player newPlayer = new Player(USERNAME, true);
                Map<String, Player> playerMap = new HashMap<String, Player>();
                playerMap.put(USERNAME, newPlayer);
                Room newRoom = new Room(randomNumber, playerMap, maxPlayers, isLock);
                databaseReference.child(String.valueOf(randomNumber))
                        .setValue(newRoom).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getActivity(), WaitingLobby.class);
                        intent.putExtra("ROOMID", String.valueOf(randomNumber));
                        intent.putExtra("USERNAME", USERNAME);
                        startActivity(intent);
                    }
                }).addOnFailureListener(e -> {
                    binding.multiplayerProgressBar.setVisibility(View.GONE);
                    binding.createRoomButton.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Something Went Wrong! Try Again Later", Toast.LENGTH_SHORT).show();
                });
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        maxPlayers = Integer.parseInt(maxPlayersSpinner[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getContext(), "Please Select One Option", Toast.LENGTH_SHORT).show();
    }
}
