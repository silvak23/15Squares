package com.example.a15squares;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * MainActivity for the 15 Squares Program
 *
 * @author **** Kamalii Silva ****
 * @version **** 24 September 2021 ****
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting variables from layout
        Button resetButton = findViewById(R.id.resetButton);
        TextView titleText = findViewById(R.id.titleTextView);
        SeekBar sizeBar = findViewById(R.id.sizeBar);
        BoardView squaresView = findViewById(R.id.boardView);

        //initializing boardController class
        BoardController squaresController = new BoardController(squaresView);

        //Setting Listeners
        resetButton.setOnClickListener(squaresController);
        sizeBar.setOnSeekBarChangeListener(squaresController);
        squaresView.setOnTouchListener(squaresView);
        titleText.setOnTouchListener(squaresController);
    }
}