package com.example.a15squares;

import android.graphics.Paint;

public class Square {

    private float x;
    private float y;
    private int row;
    private int col;
    private int num;
    private Paint sqColor = new Paint();

    Square(int r, int c, Paint paint){
        x = 1;
        y = 1;
        row = r;
        col = c;
        num = -1;
        sqColor = paint;
    }

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
