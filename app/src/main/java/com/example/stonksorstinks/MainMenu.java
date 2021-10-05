package com.example.stonksorstinks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends AppCompatActivity {

    EditText usernameEditText;
    Button singlePlayerBtn, multiplayerBtn, tutorialBtn;
    final String regex = "[^a-zA-Z0-9]+";
    Pattern p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        usernameEditText = findViewById(R.id.username_edittext);
        singlePlayerBtn = findViewById(R.id.single_player_button);
        multiplayerBtn = findViewById(R.id.multi_player_button);
        tutorialBtn = findViewById(R.id.tutorial_button);

        singlePlayerBtn.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            if (username.equals("")) {
                Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        multiplayerBtn.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();

            p = Pattern.compile(regex);
            Matcher m = p.matcher(username);

            if (username.equals("")) {
                Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
            }else{
                if(m.matches()){
                    Toast.makeText(this, "Username Cannot Contain Special Characters", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(this,MultiplayerLobby.class);
                    intent.putExtra("USERNAME",username);
                    startActivity(intent);
                }
            }
        });

        tutorialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this,Tutorial.class));
            }
        });

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
            builder.setTitle("Exit Game?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> MainMenu.super.onBackPressed());
            builder.setNegativeButton("No", (dialogInterface, i) -> alertDialog.dismiss());
        }else{
            builder = new AlertDialog.Builder(this, R.style.AlertDialogStyleDark);
            AlertDialog alertDialog = builder.create();
            builder.setTitle("Exit Game?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> MainMenu.super.onBackPressed());
            builder.setNegativeButton("No", (dialogInterface, i) -> alertDialog.dismiss());
        }
        builder.show();

    }
}