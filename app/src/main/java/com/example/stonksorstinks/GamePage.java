package com.example.stonksorstinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.stonksorstinks.Utils.BottomModalSheet;
import com.example.stonksorstinks.Utils.BottomModalSheetLogs;
import com.example.stonksorstinks.models.Cards;
import com.example.stonksorstinks.models.Player;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GamePage extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String roomID, username;
    CountDownTimer countDownTimer;

    TextView roundNumber, timer, wockRate, hdfcRate, tataRate, ongcRate, relRate, infoRate,
            actionOneTotal, actionTwoTotal, actionThreeTotal,
            wockHold, hdfcHold, tataHold, ongcHold, relHold, infoHold, cash;
    Button submitOne, submitTwo, submitThree, showCards, showLogs;

    BottomModalSheet cardsDialog;
    BottomModalSheetLogs logsDialog;

    Spinner actionOneSpinner, actionTwoSpinner, actionThreeSpinner, companyOneSpinner, companyTwoSpinner, companyThreeSpinner,
            quantityOne, quantityTwo, quantityThree;

    ViewFlipper viewFlipper;
    private float initialX;
    int Counter = 60;
    int round;

    final String[] actions = {"Buy", "Sell", "Pass"};
    final String[] companies = {"Wockhardt", "HDFC", "TATA", "ONGC", "Reliance", "Infosys"};
    final String[] quantities = {"100", "200", "300", "400", "500", "1000", "1500", "2000", "2500", "3000", "3500",
            "4000", "4500", "5000", "7500", "10000"};

    String selectedCompany, selectedQuantity;
    Boolean isRetrieved = false;
    private Player finalPlayer;
    ArrayList<Cards> arrayList;

    String logs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        Intent previousIntent = getIntent();
        roomID = previousIntent.getStringExtra("ROOMID");
        username = previousIntent.getStringExtra("USERNAME");

        roundNumber = findViewById(R.id.round_number);
        timer = findViewById(R.id.round_timer);
        cash = findViewById(R.id.cash);

        wockRate = findViewById(R.id.wock_rate);
        hdfcRate = findViewById(R.id.hdfc_rate);
        tataRate = findViewById(R.id.tata_rate);
        ongcRate = findViewById(R.id.ongc_rate);
        relRate = findViewById(R.id.reliance_rate);
        infoRate = findViewById(R.id.infosys_rate);

        wockHold = findViewById(R.id.wock_hold);
        hdfcHold = findViewById(R.id.hdfc_hold);
        tataHold = findViewById(R.id.tata_hold);
        ongcHold = findViewById(R.id.ongc_hold);
        relHold = findViewById(R.id.rel_hold);
        infoHold = findViewById(R.id.info_hold);

        actionOneTotal = findViewById(R.id.total_one);
        actionTwoTotal = findViewById(R.id.total_two);
        actionThreeTotal = findViewById(R.id.total_three);

        submitOne = findViewById(R.id.submit_one);
        submitTwo = findViewById(R.id.submit_two);
        submitThree = findViewById(R.id.submit_three);

        showCards = findViewById(R.id.show_cards);
        showLogs = findViewById(R.id.show_logs);

        actionOneSpinner = findViewById(R.id.action_one);
        actionTwoSpinner = findViewById(R.id.action_two);
        actionThreeSpinner = findViewById(R.id.action_three);

        companyOneSpinner = findViewById(R.id.company_one);
        companyTwoSpinner = findViewById(R.id.company_two);
        companyThreeSpinner = findViewById(R.id.company_three);

        quantityOne = findViewById(R.id.quantity_one);
        quantityTwo = findViewById(R.id.quantity_two);
        quantityThree = findViewById(R.id.quantity_three);

        viewFlipper = findViewById(R.id.view_flipper);


        ArrayAdapter actionAdapter = new ArrayAdapter(this, R.layout.spinner_layout, actions);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionOneSpinner.setAdapter(actionAdapter);
        actionTwoSpinner.setAdapter(actionAdapter);
        actionThreeSpinner.setAdapter(actionAdapter);

        ArrayAdapter companyAdapter = new ArrayAdapter(this, R.layout.spinner_layout, companies);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companyOneSpinner.setAdapter(companyAdapter);
        companyTwoSpinner.setAdapter(companyAdapter);
        companyThreeSpinner.setAdapter(companyAdapter);

        ArrayAdapter quantityAdapter = new ArrayAdapter(this, R.layout.spinner_layout, quantities);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityOne.setAdapter(quantityAdapter);
        quantityTwo.setAdapter(quantityAdapter);
        quantityThree.setAdapter(quantityAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomID");
        databaseReference.child(roomID).child("cards").setValue(null);
        databaseReference.child(roomID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                roundNumber.setText("Round " + dataSnapshot.child("round").getValue().toString());
                round = Integer.parseInt(dataSnapshot.child("round").getValue().toString());
                int negWock = Integer.parseInt(dataSnapshot.child("wockRate").getValue().toString());
                int negHdfc = Integer.parseInt(dataSnapshot.child("hdfcRate").getValue().toString());
                int negTata = Integer.parseInt(dataSnapshot.child("tataRate").getValue().toString());
                int negOngc = Integer.parseInt(dataSnapshot.child("ongcRate").getValue().toString());
                int negRel = Integer.parseInt(dataSnapshot.child("relRate").getValue().toString());
                int negInfo = Integer.parseInt(dataSnapshot.child("infoRate").getValue().toString());

                if (negWock <= 0) {
                    wockRate.setText("0");
                } else {
                    wockRate.setText(dataSnapshot.child("wockRate").getValue().toString());

                }
                if (negHdfc <= 0) {
                    hdfcRate.setText("0");
                } else {
                    hdfcRate.setText(dataSnapshot.child("hdfcRate").getValue().toString());

                }
                if (negTata <= 0) {
                    tataRate.setText("0");
                } else {
                    tataRate.setText(dataSnapshot.child("tataRate").getValue().toString());
                }
                if (negOngc <= 0) {
                    ongcRate.setText("0");
                } else {
                    ongcRate.setText(dataSnapshot.child("ongcRate").getValue().toString());
                }
                if (negRel <= 0) {
                    relRate.setText("0");
                } else {
                    relRate.setText(dataSnapshot.child("relRate").getValue().toString());
                }
                if (negInfo <= 0) {
                    infoRate.setText("0");
                } else {
                    infoRate.setText(dataSnapshot.child("infoRate").getValue().toString());

                }
                isRetrieved = true;
                databaseReference.child(roomID).child("players").child(username).get()
                        .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                finalPlayer = dataSnapshot.getValue(Player.class);
                                wockHold.setText(String.valueOf(finalPlayer.getWockHold()));
                                hdfcHold.setText(String.valueOf(finalPlayer.getHdfcHold()));
                                tataHold.setText(String.valueOf(finalPlayer.getTataHold()));
                                ongcHold.setText(String.valueOf(finalPlayer.getOngcHold()));
                                relHold.setText(String.valueOf(finalPlayer.getRelHold()));
                                infoHold.setText(String.valueOf(finalPlayer.getInfoHold()));
                                cash.setText(String.valueOf(finalPlayer.getCash()));
                            }
                        });
            }
        });

        arrayList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int RandomNumber = (int) (Math.random() * (46 - 1 + 1) + 1);
            db.collection("Cards").document(String.valueOf(RandomNumber)).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Cards cards = documentSnapshot.toObject(Cards.class);
                            databaseReference.child(roomID).child("cards").push().setValue(cards);
                            arrayList.add(cards);
                            showCards.setEnabled(true);
                        }
                    });

        }


        showCards.setOnClickListener(view -> {
            cardsDialog = new BottomModalSheet(arrayList);
            cardsDialog.show(getSupportFragmentManager(), "ModalBottomSheet");
        });

        //Company one spinner
        companyOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isRetrieved) {
                    selectedQuantity = quantityOne.getSelectedItem().toString();
                    selectedCompany = companies[i];
                    int total = getTotal(selectedQuantity, selectedCompany);
                    actionOneTotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Company two spinner
        companyTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isRetrieved) {
                    selectedQuantity = quantityTwo.getSelectedItem().toString();
                    selectedCompany = companies[i];
                    int total = getTotal(selectedQuantity, selectedCompany);
                    actionTwoTotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Company three spinner
        companyThreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isRetrieved) {
                    selectedQuantity = quantityThree.getSelectedItem().toString();
                    selectedCompany = companies[i];
                    int total = getTotal(selectedQuantity, selectedCompany);
                    actionThreeTotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Quantity one spinner
        quantityOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isRetrieved) {
                    selectedQuantity = quantities[i];
                    selectedCompany = companyOneSpinner.getSelectedItem().toString();
                    int total = getTotal(selectedQuantity, selectedCompany);
                    actionOneTotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Quantity two spinner
        quantityTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isRetrieved) {
                    selectedQuantity = quantities[i];
                    selectedCompany = companyTwoSpinner.getSelectedItem().toString();
                    int total = getTotal(selectedQuantity, selectedCompany);
                    actionTwoTotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Quantity three spinner
        quantityThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isRetrieved) {
                    selectedQuantity = quantities[i];
                    selectedCompany = companyThreeSpinner.getSelectedItem().toString();
                    int total = getTotal(selectedQuantity, selectedCompany);
                    actionThreeTotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //First action
        submitOne.setOnClickListener(view -> {

            String action = actionOneSpinner.getSelectedItem().toString();
            String company = companyOneSpinner.getSelectedItem().toString();
            int quantity = Integer.parseInt(quantityOne.getSelectedItem().toString());

            switch (action) {
                case "Pass":
                    submitOne.setEnabled(false);
                    companyOneSpinner.setEnabled(false);
                    actionOneSpinner.setEnabled(false);
                    companyOneSpinner.setClickable(false);
                    actionOneSpinner.setClickable(false);
                    break;
                case "Buy":
                    if (actionOneTotal.getText().toString().equals("")) {
                        Toast.makeText(GamePage.this, "Select Quantity", Toast.LENGTH_SHORT).show();
                    } else {
                        int total = Integer.parseInt(actionOneTotal.getText().toString());
                        int balance = Integer.parseInt(cash.getText().toString());

                        if (total > balance) {
                            Toast.makeText(GamePage.this, "Not Enough Cash", Toast.LENGTH_SHORT).show();
                        } else {
                            submitOne.setEnabled(false);
                            companyOneSpinner.setEnabled(false);
                            actionOneSpinner.setEnabled(false);
                            companyOneSpinner.setClickable(false);
                            actionOneSpinner.setClickable(false);
                            buy(company, quantity, balance - total);
                            databaseReference.child(roomID).child("playerLogs").setValue("Someone bought shares of " + company);
                        }
                    }
                    break;
                case "Sell":
                    Boolean setDisable = sell(company, quantity);
                    if (setDisable) {
                        if (actionOneTotal.getText().toString().equals("")) {
                            Toast.makeText(GamePage.this, "Select Quantity", Toast.LENGTH_SHORT).show();
                        } else {
                            submitOne.setEnabled(false);
                            companyOneSpinner.setEnabled(false);
                            actionOneSpinner.setEnabled(false);
                            companyOneSpinner.setClickable(false);
                            actionOneSpinner.setClickable(false);
                            int finalCash = finalPlayer.getCash() + Integer.parseInt(actionOneTotal.getText().toString());
                            finalPlayer.setCash(finalCash);
                            cash.setText(String.valueOf(finalCash));
                            databaseReference.child(roomID).child("playerLogs").setValue("Someone sold shares of " + company);
                        }
                    }
                    break;
            }

        });

        //Second action
        submitTwo.setOnClickListener(view -> {

            String action = actionTwoSpinner.getSelectedItem().toString();
            String company = companyTwoSpinner.getSelectedItem().toString();
            int quantity = Integer.parseInt(quantityTwo.getSelectedItem().toString());

            switch (action) {
                case "Pass":
                    submitTwo.setEnabled(false);
                    companyTwoSpinner.setEnabled(false);
                    actionTwoSpinner.setEnabled(false);
                    companyTwoSpinner.setClickable(false);
                    actionTwoSpinner.setClickable(false);
                    break;
                case "Buy":
                    if (actionTwoTotal.getText().toString().equals("")) {
                        Toast.makeText(GamePage.this, "Select Quantity", Toast.LENGTH_SHORT).show();
                    } else {
                        int total = Integer.parseInt(actionTwoTotal.getText().toString());
                        int balance = Integer.parseInt(cash.getText().toString());
                        if (total > balance) {
                            Toast.makeText(GamePage.this, "Not Enough Cash", Toast.LENGTH_SHORT).show();
                        } else {
                            submitTwo.setEnabled(false);
                            companyTwoSpinner.setEnabled(false);
                            actionTwoSpinner.setEnabled(false);
                            companyTwoSpinner.setClickable(false);
                            actionTwoSpinner.setClickable(false);
                            buy(company, quantity, balance - total);
                            databaseReference.child(roomID).child("playerLogs").setValue("Someone bought shares of " + company);
                        }
                    }
                    break;
                case "Sell":
                    Boolean setDisable = sell(company, quantity);
                    if (setDisable) {
                        if (actionTwoTotal.getText().toString().equals("")) {
                            Toast.makeText(GamePage.this, "Select Quantity", Toast.LENGTH_SHORT).show();
                        } else {
                            submitTwo.setEnabled(false);
                            companyTwoSpinner.setEnabled(false);
                            actionTwoSpinner.setEnabled(false);
                            companyTwoSpinner.setClickable(false);
                            actionTwoSpinner.setClickable(false);
                            int finalCash = finalPlayer.getCash() + Integer.parseInt(actionTwoTotal.getText().toString());
                            finalPlayer.setCash(finalCash);
                            cash.setText(String.valueOf(finalCash));
                            databaseReference.child(roomID).child("playerLogs").setValue("Someone sold shares of " + company);
                        }
                    }
                    break;
            }

        });

        //Third action
        submitThree.setOnClickListener(view -> {

            String action = actionThreeSpinner.getSelectedItem().toString();
            String company = companyThreeSpinner.getSelectedItem().toString();
            int quantity = Integer.parseInt(quantityThree.getSelectedItem().toString());

            switch (action) {
                case "Pass":
                    submitThree.setEnabled(false);
                    companyThreeSpinner.setEnabled(false);
                    companyThreeSpinner.setClickable(false);
                    actionThreeSpinner.setEnabled(false);
                    actionThreeSpinner.setClickable(false);
                    break;
                case "Buy":
                    if (actionThreeTotal.getText().toString().equals("")) {
                        Toast.makeText(GamePage.this, "Select Quantity", Toast.LENGTH_SHORT).show();
                    } else {
                        int total = Integer.parseInt(actionThreeTotal.getText().toString());
                        int balance = Integer.parseInt(cash.getText().toString());
                        if (total > balance) {
                            Toast.makeText(GamePage.this, "Not Enough Cash", Toast.LENGTH_SHORT).show();
                        } else {
                            submitThree.setEnabled(false);
                            companyThreeSpinner.setEnabled(false);
                            actionThreeSpinner.setEnabled(false);
                            companyThreeSpinner.setClickable(false);
                            actionThreeSpinner.setClickable(false);
                            buy(company, quantity, balance - total);
                            databaseReference.child(roomID).child("playerLogs").setValue("Someone bought shares of " + company);
                        }
                    }
                    break;
                case "Sell":
                    Boolean setDisable = sell(company, quantity);
                    if (setDisable) {
                        if (actionThreeTotal.getText().toString().equals("")) {
                            Toast.makeText(GamePage.this, "Select Quantity", Toast.LENGTH_SHORT).show();
                        } else {
                            submitThree.setEnabled(false);
                            companyThreeSpinner.setEnabled(false);
                            actionThreeSpinner.setEnabled(false);
                            companyThreeSpinner.setClickable(false);
                            actionThreeSpinner.setClickable(false);
                            int finalCash = finalPlayer.getCash() + Integer.parseInt(actionThreeTotal.getText().toString());
                            finalPlayer.setCash(finalCash);
                            cash.setText(String.valueOf(finalCash));
                            databaseReference.child(roomID).child("playerLogs").setValue("Someone sold shares of " + company);
                        }
                    }
                    break;
            }

        });

        countDownTimer = new CountDownTimer(61000, 1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(Counter));
                Counter--;
            }

            @Override
            public void onFinish() {

                databaseReference.child(roomID).child("players").child(username).setValue(finalPlayer);
                round++;
                databaseReference.child(roomID).child("round").setValue(round);
                Intent nextIntent = new Intent(GamePage.this, PreRound.class);
                nextIntent.putExtra("USERNAME", username);
                nextIntent.putExtra("ROOMID", roomID);
                startActivity(nextIntent);
                finish();

            }
        }.start();

        databaseReference.child(roomID).child("playerLogs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                logs = logs + "\n" + snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        showLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logsDialog = new BottomModalSheetLogs(logs);
                logsDialog.show(getSupportFragmentManager(), "ModalBottomLogs");
            }
        });

    }

    //Called when selling shares
    private Boolean sell(String company, int quantity) {
        int compare = 0;
        switch (company) {
            case "Wockhardt":
                compare = Integer.parseInt(wockHold.getText().toString());
                break;
            case "HDFC":
                compare = Integer.parseInt(hdfcHold.getText().toString());
                break;
            case "TATA":
                compare = Integer.parseInt(tataHold.getText().toString());
                break;
            case "ONGC":
                compare = Integer.parseInt(ongcHold.getText().toString());
                break;
            case "Reliance":
                compare = Integer.parseInt(relHold.getText().toString());
                break;
            case "Infosys":
                compare = Integer.parseInt(infoHold.getText().toString());
                break;
        }
        if (compare < quantity) {
            Toast.makeText(GamePage.this, "Not Enough Holdings", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            int setQuantity = compare - quantity;
            switch (company) {
                case "Wockhardt":
                    wockHold.setText(String.valueOf(setQuantity));
                    finalPlayer.setWockHold(setQuantity);
                    break;
                case "HDFC":
                    hdfcHold.setText(String.valueOf(setQuantity));
                    finalPlayer.setHdfcHold(setQuantity);
                    break;
                case "TATA":
                    tataHold.setText(String.valueOf(setQuantity));
                    finalPlayer.setTataHold(setQuantity);
                    break;
                case "ONGC":
                    ongcHold.setText(String.valueOf(setQuantity));
                    finalPlayer.setOngcHold(setQuantity);
                    break;
                case "Reliance":
                    relHold.setText(String.valueOf(setQuantity));
                    finalPlayer.setRelHold(setQuantity);
                    break;
                case "Infosys":
                    infoHold.setText(String.valueOf(setQuantity));
                    finalPlayer.setInfoHold(setQuantity);
                    break;
            }

            return true;
        }
    }

    //Called when buying shares
    private void buy(String company, int quantity, int remain) {
        switch (company) {
            case "Wockhardt":
                wockHold.setText(String.valueOf(finalPlayer.getWockHold() + quantity));
                finalPlayer.setWockHold(finalPlayer.getWockHold() + quantity);
                break;
            case "HDFC":
                hdfcHold.setText(String.valueOf(finalPlayer.getHdfcHold() + quantity));
                finalPlayer.setHdfcHold(finalPlayer.getHdfcHold() + quantity);
                break;
            case "TATA":
                tataHold.setText(String.valueOf(finalPlayer.getTataHold() + quantity));
                finalPlayer.setTataHold(finalPlayer.getTataHold() + quantity);
                break;
            case "ONGC":
                ongcHold.setText(String.valueOf(finalPlayer.getOngcHold() + quantity));
                finalPlayer.setOngcHold(finalPlayer.getOngcHold() + quantity);
                break;
            case "Reliance":
                relHold.setText(String.valueOf(finalPlayer.getRelHold() + quantity));
                finalPlayer.setRelHold(finalPlayer.getRelHold() + quantity);
                break;
            case "Infosys":
                infoHold.setText(String.valueOf(finalPlayer.getInfoHold() + quantity));
                finalPlayer.setInfoHold(finalPlayer.getInfoHold() + quantity);
                break;
        }
        finalPlayer.setCash(remain);
        cash.setText(String.valueOf(remain));
    }

    //Called when spinners are touched
    private int getTotal(String selectedQuantity, String selectedCompany) {
        String companyPrice = "";
        int quantitySelected = 0;
        switch (selectedCompany) {
            case "Wockhardt":
                companyPrice = wockRate.getText().toString();
                break;
            case "HDFC":
                companyPrice = hdfcRate.getText().toString();
                break;
            case "TATA":
                companyPrice = tataRate.getText().toString();
                break;
            case "ONGC":
                companyPrice = ongcRate.getText().toString();
                break;
            case "Reliance":
                companyPrice = relRate.getText().toString();
                break;
            case "Infosys":
                companyPrice = infoRate.getText().toString();
                break;
        }
        switch (selectedQuantity) {
            case "100":
                quantitySelected = 100;
                break;
            case "200":
                quantitySelected = 200;
                break;
            case "300":
                quantitySelected = 300;
                break;
            case "400":
                quantitySelected = 400;
                break;
            case "500":
                quantitySelected = 500;
                break;
            case "1000":
                quantitySelected = 1000;
                break;
            case "1500":
                quantitySelected = 1500;
                break;
            case "2000":
                quantitySelected = 2000;
                break;
            case "2500":
                quantitySelected = 2500;
                break;
            case "3000":
                quantitySelected = 3000;
                break;
            case "3500":
                quantitySelected = 3500;
                break;
            case "4000":
                quantitySelected = 4000;
                break;
            case "4500":
                quantitySelected = 4500;
                break;
            case "5000":
                quantitySelected = 5000;
                break;
            case "7500":
                quantitySelected = 7500;
                break;
            case "10000":
                quantitySelected = 10000;
                break;
        }

        return Integer.parseInt(companyPrice) * quantitySelected;
    }

    //Swipe the rates view
    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
                    viewFlipper.setInAnimation(this, R.anim.in_right);
                    viewFlipper.setInAnimation(this, R.anim.out_left);
                    viewFlipper.showNext();
                } else {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;
                    viewFlipper.setOutAnimation(this, R.anim.in_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_right);
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
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
        if (!isDark) {
            builder = new AlertDialog.Builder(this, R.style.AlertDialogStyleLight);
            AlertDialog alertDialog = builder.create();
            builder.setTitle("Leave Game and return to main menu?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                databaseReference.child(roomID).child("players").child(username).removeValue();
                countDownTimer.cancel();

                startActivity(new Intent(GamePage.this, MainMenu.class));
                finish();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> alertDialog.dismiss());
        } else {
            builder = new AlertDialog.Builder(this, R.style.AlertDialogStyleDark);
            AlertDialog alertDialog = builder.create();
            builder.setTitle("Leave Game and return to main menu?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                databaseReference.child(roomID).child("players").child(username).removeValue();
                countDownTimer.cancel();

                startActivity(new Intent(GamePage.this, MainMenu.class));
                finish();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> alertDialog.dismiss());

        }
        builder.show();

    }
}