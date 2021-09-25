package com.example.a15squares;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button resetButton = findViewById(R.id.resetButton);
        Button smallerButton = findViewById(R.id.smallerButton);
        Button largerButton = findViewById(R.id.largerButton);
        Button cheatButton = findViewById(R.id.cheatButton);
        BoardView squaresView = (BoardView) findViewById(R.id.boardView);
        BoardController squaresController = new BoardController(squaresView);

        cheatButton.setOnClickListener(squaresController);
        resetButton.setOnClickListener(squaresController);
        smallerButton.setOnClickListener(squaresController);
        largerButton.setOnClickListener(squaresController);
        squaresView.setOnTouchListener(squaresView);
    }
}