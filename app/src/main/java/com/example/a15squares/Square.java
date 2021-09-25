package com.example.a15squares;

import android.graphics.Paint;

/**
 * Square object that will represent each square on the board
 *
 * @author **** Kamalii Silva ****
 * @version **** 24 September 2021 ****
 */
public class Square {

    //Instanced Variables
    private float x;
    private float y;
    private int row;
    private int col;
    private int num;
    private Paint sqColor;

    /**
     * Square constructor:
     * @param r row where the object is in the grid
     * @param c column where the object is in the grid
     * @param paint color to paint the object
     */
    Square(int r, int c, Paint paint){
        x = 1;
        y = 1;
        row = r;
        col = c;
        num = -1;
        sqColor = paint;
    }

    /**
     * The rest of the methods in this class are setters and getters to access
     * the values within the object
     */
    public void setLocation(float posX, float posY){
        x = posX;
        y = posY;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setPaint(Paint paint){
        sqColor = paint;
    }

    public Paint getPaint(){
        return sqColor;
    }

    public void setNum(int n){
        num = n;
    }

    public int getNum(){
        return num;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
}
