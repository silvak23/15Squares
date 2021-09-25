package com.example.a15squares;

import android.graphics.Paint;

/**
 * BoardModel
 *      Stores information that will be communicated between the view and the controller
 *
 * @author **** Kamalii Silva ****
 * @version **** 24 September 2021 ****
 */

public class BoardModel {
    public int gridSize = 4;
    public int reset = 1;
    Square empty = new Square(gridSize, gridSize, new Paint());
    int reDraw = 0;
    public float textSize = 0.0f;
}
