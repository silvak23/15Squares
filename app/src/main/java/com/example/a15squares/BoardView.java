package com.example.a15squares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * BoardView
 *      The surfaceView subclass that will hold and access, and print the board itself
 *
 *      -print statements show the number in an object and where it is in the grid
 *
 * @author **** Kamalii Silva ****
 * @version **** 24 September 2021 ****
 */
public class BoardView extends SurfaceView implements View.OnTouchListener{

    //Paints
    Paint whitePaint = new Paint();
    Paint correctPaint = new Paint();
    Paint greyPaint = new Paint();
    Paint textPaint = new Paint();

    //Location Variables
    public static final float gap = 10.0f;
    public float xMax;
    public float yMax;
    public float xOff;
    public float yOff;
    public float touchX;
    public float touchY;
    public float squareSize;

    private int correct;
    private BoardModel boardModel;

    //Board ArrayLists
    public ArrayList<ArrayList<Square>> squareList;
    ArrayList<ArrayList<Integer>> correctList;

    /**
     * BoardView Constructor
     * @param context for the super
     * @param attrs for the super
     */
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        setBackgroundColor(0xFFFFFFFF);
        whitePaint.setColor(0xFFFFFFFF);
        correctPaint.setColor(0xFFDCD0FF);
        greyPaint.setColor(0xFF969696);
        textPaint.setColor(0xFF000000);

        boardModel = new BoardModel();
        squareList = new ArrayList<>();
        correctList = new ArrayList<>();


        touchX = -1;
        touchY = -1;
        xMax = 400;
        yMax = 400;
        xOff = 0;
        yOff = 0;

        //INITIALIZE THE 2D ARRAYLISTS
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

    /**
     * OnDraw
     *      Draws onto the boardView on the screen
     * @param canvas where we want to draw
     */
    @Override
    public void onDraw(Canvas canvas){
        reSize();

        //SET LOCATIONS
        xMax = canvas.getWidth();
        yMax = canvas.getHeight();
        if(xMax < yMax) {
            squareSize = ((xMax -(boardModel.gridSize+6.0f)*gap)/ (boardModel.gridSize*1.0f));
        }
        else{
            squareSize = ((yMax -(boardModel.gridSize+6.0f)*gap)/ (boardModel.gridSize*1.0f));
        }
        xOff = (xMax -((squareSize+gap)*boardModel.gridSize+gap))/2.0f;
        yOff = (yMax -((squareSize+gap)*boardModel.gridSize+gap))/2.0f;
        boardModel.textSize = squareSize-40.0f;

        //Draws Background Rectangle
        canvas.drawRect(xOff, yOff, xMax - xOff, yMax - yOff, greyPaint);
        where();

        //Reset the board
        if(boardModel.reset != 0) {
            reRoll();
        }

        //DRAWS THE BOARD
        for(int i = 0; i < boardModel.gridSize; i++) {
            for(int q = 0; q < boardModel.gridSize; q++) {
                System.out.println(squareList.get(i).get(q).getNum()+ ": " +squareList.get(i).get(q).getX()
                        + "-" + squareList.get(i).get(q).getY());
                squareList.get(i).get(q).setPaint(whitePaint);

                //CHECKS IF THE SQUARE IS IN THE RIGHT LOCATION
                if(squareList.get(i).get(q).getNum() == correctList.get(i).get(q)){
                    squareList.get(i).get(q).setPaint(correctPaint);
                    correct++;
                }

                //PAINT SQUARES
                if (squareList.get(i).get(q).getNum() != boardModel.gridSize* boardModel.gridSize) {
                    canvas.drawRect(squareList.get(i).get(q).getX(), squareList.get(i).get(q).getY(),
                            squareList.get(i).get(q).getX() + squareSize,
                            squareList.get(i).get(q).getY() + squareSize,
                            squareList.get(i).get(q).getPaint());
                    //PAINT LETTERS
                    textPaint.setTextSize(boardModel.textSize);
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(squareList.get(i).get(q).getNum() + "",
                            squareList.get(i).get(q).getX() + (squareSize / 2.0f),
                            squareList.get(i).get(q).getY() + (squareSize / 2.0f)
                                    + (boardModel.textSize/3), textPaint);
                }
                //KEEP TRACK OF THE BLANK SPOT ON THE BOARD
                else{
                    boardModel.empty = squareList.get(i).get(q);
                }
            }
        }
        //PRINTS A WINNING STATEMENT IF EVERYTHING IS IN THE RIGHT PLACE
        if(correct == boardModel.gridSize*boardModel.gridSize){
            whitePaint.setTextSize(boardModel.textSize*boardModel.gridSize/4);
            whitePaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("You Win!", xMax /2, yMax /2+(boardModel.textSize/3), whitePaint);
        }
        correct = 0;
    }

    /**
     * getBoardModel
     * @return reference to the same boardModel object
     */
    public BoardModel getBoardModel(){
        return boardModel;
    }

    /**
     * where
     *  helper method to set the x coord and y coord for each object on the board
     */
    private void where(){
        int num = 0;
        for(int i = 0; i < boardModel.gridSize; i++){
            for(int q = 0; q < boardModel.gridSize; q++){
                squareList.get(i).get(q).setLocation(gap+ xOff +(q*(squareSize+gap)),
                        gap+ yOff +(i*(gap+squareSize)));
                System.out.println((num) + ": " + squareList.get(i).get(q).getX()+ "-"
                        + squareList.get(i).get(q).getY());
                num++;
            }
        }
    }

    /**
     * reRoll
     *      fills up the shown array and correct array with their values
     *      then if asked will randomly rearrange squares in the shown array.
     */
    private void reRoll(){
        int run = 0;
        int holdPlaceX1;
        int holdPlaceY1;
        int holdPlaceX2;
        int holdPlaceY2;
        int num = 1;
        //FILLS IN BOTH ARRAYS WITH THE CORRECT NUMBERS
        for(int e = 0; e < boardModel.gridSize; e++){
            for(int w = 0; w < boardModel.gridSize; w++) {
                squareList.get(e).get(w).setNum(num);
                correctList.get(e).set(w, new Integer(num));
                num++;
            }
        }
        /*REARRANGES THE SQUARES RANDOMLY
                leaves the blank square in the bottom right
         */
        if(boardModel.reset >= 0) {
            while (run < (randomNumber() * 20) + 20) {
                holdPlaceX1 = randomNumber();
                holdPlaceY1 = randomNumber();
                holdPlaceX2 = randomNumber();
                holdPlaceY2 = randomNumber();
                if((holdPlaceX1 + holdPlaceX1 != ((boardModel.gridSize + boardModel.gridSize)-2)) &&
                        (holdPlaceX2 + holdPlaceY2 != ((boardModel.gridSize + boardModel.gridSize)-2))) {
                    swap(squareList.get(holdPlaceX1).get(holdPlaceY1),
                            squareList.get(holdPlaceX2).get(holdPlaceY2));
                    run++;
                }
            }
        }
        boardModel.reset=0;
    }

    /**
     * randomNumber
     *      helper function to get a random number
     * @return a random number from 0 to the size of the grid
     */
    private int randomNumber(){
        Random rand = new Random();
        int upperbound = boardModel.gridSize;
        int toReturn = rand.nextInt(upperbound);
        return toReturn;
    }

    /**
     * onTouch
     *      finds each square next to the empty and checks if one of those were clicked
     *      then swaps it with the empty square
     * @param view that is being touched
     * @param event touch action
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            Square hold;
            System.out.println(boardModel.empty.getNum() + ": " + boardModel.empty.getRow()
                    + "-" + boardModel.empty.getCol());
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

    /**
     * swap
     *      helper method for swapping the number in two Square objects
     * @param one square object
     * @param two square object
     */
    public void swap(Square one, Square two){
        int hold;
        hold = one.getNum();
        one.setNum(two.getNum());
        two.setNum(hold);
    }

    /**
     * reSize
     *      method for resizing each arrayList
     */
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
