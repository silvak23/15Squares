package com.example.a15squares;

import android.graphics.Paint;

public class BoardModel {
    public int gridSize = 4;
    public int reset = 1;
    Square empty = new Square(gridSize, gridSize, new Paint());
    int reDraw = 0;
    public float textSize = 100.0f;
}
