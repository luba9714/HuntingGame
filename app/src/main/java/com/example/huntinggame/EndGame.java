package com.example.huntinggame;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textview.MaterialTextView;

public class EndGame extends AppCompatActivity {
    private MaterialTextView endGame_LBL_title,endGame_LBL_result;
    private Button endGame_BTN_finish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);
        endGame_LBL_result=findViewById(R.id.endGame_LBL_result);
        endGame_BTN_finish=findViewById(R.id.endGame_BTN_finish);
        endGame_LBL_result.setText(getIntent().getStringExtra("SCORE"));
        endGame_BTN_finish.setOnClickListener(view -> finish());
    }
}
