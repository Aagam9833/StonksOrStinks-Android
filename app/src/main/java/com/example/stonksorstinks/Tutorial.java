package com.example.stonksorstinks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Tutorial extends AppCompatActivity {

    Button nextTutorial;
    RelativeLayout relativeLayout;
    int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        relativeLayout = findViewById(R.id.tutorial_layout);
        nextTutorial = findViewById(R.id.next_tutorial);

        nextTutorial.setOnClickListener(view -> {

            switch (i){
                case 1:
                    relativeLayout.setBackgroundResource(R.drawable.sos_tutorial_two);
                    break;
                case 2:
                    relativeLayout.setBackgroundResource(R.drawable.sos_tutorial_three);
                    break;
                case 3:
                    relativeLayout.setBackgroundResource(R.drawable.sos_tutorial_four);
                    break;
                case 4:
                    relativeLayout.setBackgroundResource(R.drawable.sos_tutorial_five);
                    nextTutorial.setText("Finish");
                    break;
                case 5:
                    finish();
            }
            i++;
        });

    }
}