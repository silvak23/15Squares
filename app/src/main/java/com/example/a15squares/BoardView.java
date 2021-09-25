package com.example.a15squares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.fonts.FontStyle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class BoardView extends SurfaceView implements View.OnTouchListener{

    //Paints
    Paint whitePaint = new Paint();
    Paint correctPaint = new Paint();
    Paint greyPaint = new Paint();
    Paint textPaint = new Paint();

    //Location Variables
    public static final float gap = 10.0f;
    public float xmax;
    public float ymax;
    public float xoff;
    public float yoff;
    public float touchX;
    public float touchY;
    public float squareSize;
    private int correct;

    private BoardModel boardModel;
    public ArrayList<ArrayList<Square>> squareList;
    ArrayList<ArrayList<Integer>> correctList;


    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        setBackgroundColor(0xFFFFFFFF);
        whitePaint.setColor(0xFFFFFFFF);
        correctPaint.setColor(0xFFDCD0FF);
        greyPaint.setColor(0xFF969696);
        textPaint.setColor(0xFF000000);

        boardModel = new BoardModel();
        squareList = new ArrayList<ArrayList<Square>>();
        correctList = new ArrayList<ArrayList<Integer>>();


        touchX = -1;
        touchY = -1;
        xmax = 400;
        ymax = 400;
        xoff = 0;
        yoff = 0;

        int num = 1;
        for (int i = 0; i < boardModel.gridSize; i++) {
            squareList.add(new ArrayList<>());
            correctList.add(new ArrayList<>());
            for (int q = 0; q < boardModel.gridSize; q++) {
                squareList.get(i).add(new Square(i, q, whitePaint));
                correctList.get(i).add(new Integer(num));
                num++;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        reSize();
        //SET LOCATIONS
        xmax = canvas.getWidth();
        ymax = canvas.getHeight();
        if(xmax < ymax) {
            squareSize = ((xmax-(boardModel.gridSize+6.0f)*gap)/ (boardModel.gridSize*1.0f));
        }
        else{
            squareSize = ((ymax-(boardModel.gridSize+6.0f)*gap)/ (boardModel.gridSize*1.0f));
        }
        xoff = (xmax-((squareSize+gap)*boardModel.gridSize+gap))/2.0f;
        yoff = (ymax-((squareSize+gap)*boardModel.gridSize+gap))/2.0f;
        canvas.drawRect(xoff, yoff, xmax-xoff, ymax-yoff, greyPaint);
        boardModel.textSize = squareSize-40.0f;
        where();
        if(boardModel.reset != 0) {
            reRoll();
        }
        for(int i = 0; i < boardModel.gridSize; i++) {
            for(int q = 0; q < boardModel.gridSize; q++) {
                System.out.println(squareList.get(i).get(q).getNum() + ": " + squareList.get(i).get(q).getX() + "-" + squareList.get(i).get(q).getY());
                squareList.get(i).get(q).setPaint(whitePaint);
                if(squareList.get(i).get(q).getNum() == correctList.get(i).get(q)){
                    squareList.get(i).get(q).setPaint(correctPaint);
                    correct++;
                }
                //PAINT SQUARES
                if (squareList.get(i).get(q).getNum() != boardModel.gridSize* boardModel.gridSize) {
                    canvas.drawRect(squareList.get(i).get(q).getX(), squareList.get(i).get(q).getY(),
                            squareList.get(i).get(q).getX() + squareSize, squareList.get(i).get(q).getY() + squareSize,
                            squareList.get(i).get(q).getPaint());
                    //PAINT LETTERS
                    textPaint.setTextSize(boardModel.textSize);
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(squareList.get(i).get(q).getNum() + "", squareList.get(i).get(q).getX() + (squareSize / 2.0f),
                            squareList.get(i).get(q).getY() + (squareSize / 2.0f) + (boardModel.textSize/3), textPaint);
                }
                else{
                    boardModel.empty = squareList.get(i).get(q);
                }
            }
        }
        if(correct == boardModel.gridSize*boardModel.gridSize){
            whitePaint.setTextSize(boardModel.textSize*boardModel.gridSize/4);
            whitePaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("You Win!", xmax/2, ymax/2+(boardModel.textSize/3), whitePaint);
        }
        correct = 0;
    }

    public BoardModel getBoardModel(){
        return boardModel;
    }

    private void where(){
        int num = 0;
        for(int i = 0; i < boardModel.gridSize; i++){
            for(int q = 0; q < boardModel.gridSize; q++){
                squareList.get(i).get(q).setLocation(gap+xoff+(q*(squareSize+gap)), gap+yoff+(i*(gap+squareSize)));
                System.out.println((num) + ": " + squareList.get(i).get(q).getX()+ "-" + squareList.get(i).get(q).getY());
                num++;
            }
        }
    }

    private void reRoll(){
        int run = 0;
        int holdPlaceX1;
        int holdPlaceY1;
        int holdPlaceX2;
        int holdPlaceY2;
        int num = 1;
        for(int e = 0; e < boardModel.gridSize; e++){
            for(int w = 0; w < boardModel.gridSize; w++) {
                squareList.get(e).get(w).setNum(num);
                correctList.get(e).set(w, new Integer(num));
                num++;
            }
        }
        if(boardModel.reset >= 0) {
            while (run < (randomNumber() * 20) + 20) {
                holdPlaceX1 = randomNumber();
                holdPlaceY1 = randomNumber();
                holdPlaceX2 = randomNumber();
                holdPlaceY2 = randomNumber();
                swap(squareList.get(holdPlaceX1).get(holdPlaceY1), squareList.get(holdPlaceX2).get(holdPlaceY2));
                run++;
            }
        }
        boardModel.reset=0;
    }

    private int randomNumber(){
        Random rand = new Random();
        int upperbound = boardModel.gridSize;
        int toReturn = rand.nextInt(upperbound);
        return toReturn;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            Square hold;
            System.out.println(boardModel.empty.getNum() + ": " + boardModel.empty.getRow() + "-" + boardModel.empty.getCol());
            //LEFT
            if(boardModel.empty.getCol() != 0){
                hold = squareList.get(boardModel.empty.getRow()).get(boardModel.empty.getCol()-1);
                System.out.println(hold.getNum() + ": " + hold.getRow() + "-" + hold.getCol());
                if(hold.getX() <= touchX && touchX <= hold.getX()+squareSize){
                    if(hold.getY() <= touchY && touchY <= hold.getY()+squareSize){
                        swap(hold, boardModel.empty);
                        view.invalidate();
                        return true;
                    }
                }
            }
            //TOP
            if(boardModel.empty.getRow() != 0){
                hold = squareList.get(boardModel.empty.getRow()-1).get(boardModel.empty.getCol());
                System.out.println(hold.getNum() + ": " + hold.getRow() + "-" + hold.getCol());
                if(hold.getX() <= touchX && touchX <= hold.getX()+squareSize){
                    if(hold.getY() <= touchY && touchY <= hold.getY()+squareSize){
                        swap(hold, boardModel.empty);
                        view.invalidate();
                        return true;
                    }
                }
            }
            //RIGHT
            if(boardModel.empty.getCol() < boardModel.gridSize-1){
                hold = squareList.get(boardModel.empty.getRow()).get(boardModel.empty.getCol()+1);
                System.out.println(hold.getNum() + ": " + hold.getRow() + "-" + hold.getCol());
                if(hold.getX() <= touchX && touchX <= hold.getX()+squareSize){
                    if(hold.getY() <= touchY && touchY <= hold.getY()+squareSize){
                        swap(hold, boardModel.empty);
                        view.invalidate();
                        return true;
                    }
                }
            }
            //BOTTOM
            if(boardModel.empty.getRow() < boardModel.gridSize-1){
                hold = squareList.get(boardModel.empty.getRow()+1).get(boardModel.empty.getCol());
                System.out.println(hold.getNum() + ": " + hold.getRow() + "-" + hold.getCol());
                if(hold.getX() <= touchX && touchX <= hold.getX()+squareSize){
                    if(hold.getY() <= touchY && touchY <= hold.getY()+squareSize){
                        swap(hold, boardModel.empty);
                        view.invalidate();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Helper method to swap the numbers stored in two Square objects
    public void swap(Square one, Square two){
        int hold;
        hold = one.getNum();
        one.setNum(two.getNum());
        two.setNum(hold);
    }

    public void reSize(){
        int prevSize = boardModel.gridSize;
        //BIGGER
        if(boardModel.reDraw > 0){
            boardModel.gridSize += 1;
            squareList.add(new ArrayList<Square>());
            correctList.add(new ArrayList<Integer>());
            for (int q = 0; q < boardModel.gridSize; q++) {
                squareList.get(prevSize).add(new Square(prevSize, q, whitePaint));
                squareList.get(q).add(new Square(q, prevSize, whitePaint));
                correctList.get(q).add(new Integer(0));
                correctList.get(prevSize).add(new Integer(0));
            }
            boardModel.reDraw = 0;
        }
        //SMALLER
        if(boardModel.reDraw < 0){
            boardModel.gridSize -= 1;
            for (int i = boardModel.gridSize; i > prevSize; i--){
                for (int q = boardModel.gridSize; q > 0; q--) {
                    squareList.remove(squareList.get(i).get(q));
                }
                squareList.remove(squareList.get(i));
            }
            boardModel.reDraw = 0;
        }
    }

}
