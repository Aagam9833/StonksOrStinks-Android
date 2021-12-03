package com.example.stonksorstinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stonksorstinks.models.Cards;
import com.example.stonksorstinks.models.Player;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreRound extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView wockRateView, hdfcRateView, tataRateView, ongcRateView, relRateView, infoRateView;
    int wockRate, hdfcRate, tataRate, ongcRate, relRate, infoRate;
    int roundNumber;
    BarChart barChart;
    Boolean isHost = false;
    LinearLayout nextRoundLayout;
    ValueEventListener valueEventListener;
    Button readyButton;
    int highestNet = 0;
    String winner = "";
    ProgressBar preRoundLoading;
    LinearLayout preRoundLayout;

    String roomID;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_round);

        Intent previousIntent = getIntent();
        roomID = previousIntent.getStringExtra("ROOMID");
        username = previousIntent.getStringExtra("USERNAME");

        readyButton = findViewById(R.id.ready_next_round);
        nextRoundLayout = findViewById(R.id.next_round_layout);

        preRoundLoading = findViewById(R.id.preround_loading);
        preRoundLayout = findViewById(R.id.preround_layout);

        barChart = findViewById(R.id.bar_chart);
        wockRateView = findViewById(R.id.wock_rate_pre_round);
        hdfcRateView = findViewById(R.id.hdfc_rate_pre_round);
        tataRateView = findViewById(R.id.tata_rate_pre_round);
        ongcRateView = findViewById(R.id.ongc_rate_pre_round);
        relRateView = findViewById(R.id.reliance_rate_pre_round);
        infoRateView = findViewById(R.id.infosys_rate_pre_round);

        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                preRoundLoading.setVisibility(View.GONE);
                preRoundLayout.setVisibility(View.VISIBLE);

                databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");

                databaseReference.child(roomID).child("players").child(username).child("ready").setValue(false);
                databaseReference.child(roomID).get()
                        .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                isHost = (Boolean) dataSnapshot.child("players").child(username).child("host").getValue();
                                roundNumber = Integer.parseInt(dataSnapshot.child("round").getValue().toString());

                                wockRate = Integer.parseInt(dataSnapshot.child("wockRate").getValue().toString());
                                hdfcRate = Integer.parseInt(dataSnapshot.child("hdfcRate").getValue().toString());
                                tataRate = Integer.parseInt(dataSnapshot.child("tataRate").getValue().toString());
                                ongcRate = Integer.parseInt(dataSnapshot.child("ongcRate").getValue().toString());
                                relRate = Integer.parseInt(dataSnapshot.child("relRate").getValue().toString());
                                infoRate = Integer.parseInt(dataSnapshot.child("infoRate").getValue().toString());

                                for (DataSnapshot ds : dataSnapshot.child("cards").getChildren()) {
                                    Cards cards = ds.getValue(Cards.class);
                                    switch (cards.getName()) {
                                        case "Wockhardt":
                                            wockRate = wockRate + cards.getPoints();
                                            break;
                                        case "HDFC":
                                            hdfcRate = hdfcRate + cards.getPoints();
                                            break;
                                        case "TATA":
                                            tataRate = tataRate + cards.getPoints();
                                            break;
                                        case "ONGC":
                                            ongcRate = ongcRate + cards.getPoints();
                                            break;
                                        case "Reliance":
                                            relRate = relRate + cards.getPoints();
                                            break;
                                        case "Infosys":
                                            infoRate = infoRate + cards.getPoints();
                                            break;
                                    }
                                }

                                if(wockRate <= 0){
                                    wockRate = 0;
                                }
                                if(hdfcRate <= 0){
                                    hdfcRate = 0;
                                }
                                if(tataRate <= 0){
                                    tataRate = 0;
                                }
                                if(ongcRate <= 0){
                                    ongcRate = 0;
                                }
                                if(relRate <= 0){
                                    relRate = 0;
                                }
                                if(infoRate <= 0){
                                    infoRate = 0;
                                }

                                wockRateView.setText(String.valueOf(wockRate));
                                hdfcRateView.setText(String.valueOf(hdfcRate));
                                tataRateView.setText(String.valueOf(tataRate));
                                ongcRateView.setText(String.valueOf(ongcRate));
                                relRateView.setText(String.valueOf(relRate));
                                infoRateView.setText(String.valueOf(infoRate));

                                ArrayList<BarEntry> networth = new ArrayList<>();

                                int i = 0;
                                String[] names = new String[(int) dataSnapshot.child("players").getChildrenCount()];
                                for (DataSnapshot ds : dataSnapshot.child("players").getChildren()) {

                                    Player player = ds.getValue(Player.class);
                                    names[i] = player.getUsername();
                                    int worth = player.getCash() + (player.getWockHold() * wockRate) +
                                            (player.getHdfcHold() * hdfcRate) +
                                            (player.getTataHold() * tataRate) +
                                            (player.getOngcHold() * ongcRate) +
                                            (player.getRelHold() * relRate) +
                                            (player.getInfoHold() * infoRate);
                                    if(highestNet < worth){
                                        highestNet = worth;
                                        winner = player.getUsername();
                                    }
                                    networth.add(new BarEntry(i, worth));
                                    i++;
                                }


                                BarDataSet barDataSet = new BarDataSet(networth, "Networth");
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextColor(Color.GREEN);
                                barDataSet.setValueTextSize(16f);

                                BarData barData = new BarData(barDataSet);

                                XAxis xAxis = barChart.getXAxis();
                                YAxis yAxis = barChart.getAxisLeft();
                                yAxis.setTextColor(Color.GREEN);
                                yAxis = barChart.getAxisRight();
                                yAxis.setTextColor(Color.GREEN);
                                xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
                                xAxis.setCenterAxisLabels(true);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setTextColor(Color.GREEN);
                                barChart.setFitBars(true);
                                barChart.setData(barData);
                                barChart.animateY(500);
                                Description description = new Description();
                                description.setText("");

                                if (isHost) {
                                    databaseReference.child(roomID).child("wockRate").setValue(wockRate);
                                    databaseReference.child(roomID).child("hdfcRate").setValue(hdfcRate);
                                    databaseReference.child(roomID).child("tataRate").setValue(tataRate);
                                    databaseReference.child(roomID).child("ongcRate").setValue(ongcRate);
                                    databaseReference.child(roomID).child("relRate").setValue(relRate);
                                    databaseReference.child(roomID).child("infoRate").setValue(infoRate);
                                }
                            }
                        });

                valueEventListener = databaseReference.child(roomID).child("players").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int playerCount = (int) snapshot.getChildrenCount();
                        int i = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Player player = ds.getValue(Player.class);
                            if (player.getReady()) {
                                i++;
                            }
                        }
                        if (playerCount == i) {
                            Log.d("TAGGED", "COMPLETE");
                            databaseReference.child(roomID).child("players").removeEventListener(valueEventListener);
                            if (roundNumber == 6) {
                                Intent finalIntent = new Intent(PreRound.this, FinalActivity.class);
                                finalIntent.putExtra("WINNER",highestNet);
                                finalIntent.putExtra("WINNERNAME",winner);
                                finalIntent.putExtra("ROOMID",roomID);
                                finalIntent.putExtra("USERNAME",username);
                                startActivity(finalIntent);
                            } else {
                                Intent nextIntent = new Intent(PreRound.this, GamePage.class);
                                nextIntent.putExtra("ROOMID", roomID);
                                nextIntent.putExtra("USERNAME", username);
                                startActivity(nextIntent);
                            }
                            finish();
                        } else {
                            Log.d("TAGGED", "WAITING");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                readyButton.setOnClickListener(view -> {
                    databaseReference.child(roomID).child("players").child(username).child("ready").setValue(true);
                    databaseReference.child(roomID).child("playerLogs").setValue("");
                    readyButton.setVisibility(View.GONE);
                    nextRoundLayout.setVisibility(View.VISIBLE);
                });
            }
        }.start();



    }

    @Override
    public void onBackPressed() {
        boolean isDark = false;
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                isDark = true;
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                isDark = false;
                break;
        }
        AlertDialog.Builder builder;
        if(!isDark){
            builder = new AlertDialog.Builder(this, R.style.AlertDialogStyleLight);
            AlertDialog alertDialog = builder.create();
            builder.setTitle("Leave Game and return to main menu?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                databaseReference.child(roomID).child("players").child(username).removeValue();
                startActivity(new Intent(PreRound.this, MainMenu.class));
                finish();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> alertDialog.dismiss());
        }else{
            builder = new AlertDialog.Builder(this, R.style.AlertDialogStyleDark);
            AlertDialog alertDialog = builder.create();
            builder.setTitle("Leave Game and return to main menu?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                databaseReference.child(roomID).child("players").child(username);
                startActivity(new Intent(PreRound.this, MainMenu.class));
                finish();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> alertDialog.dismiss());
        }
        builder.show();

    }
}