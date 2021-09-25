package com.example.a15squares;


import android.view.MotionEvent;
import android.view.View;

/**
 * BoardController
 *      The listener for most of the actions, mainly the buttons, on the board
 *
 * @author **** Kamalii Silva ****
 * @version **** 24 September 2021 ****
 */
public class BoardController implements View.OnClickListener, View.OnTouchListener{

    //Instance Variables
    private BoardView boardView;
    private BoardModel boardModel;

    /**
     * BoardController Constructor
     * @param BoardViewIn the board view that it will be listening to
     */
    BoardController(BoardView BoardViewIn) {
        boardView = BoardViewIn;
        boardModel = boardView.getBoardModel();
    }

    /**
     * OnClick method for the buttons in the surface view
     * @param view that we will look at
     */
    @Override
    public void onClick(View view) {
        //RESET BUTTON
        if(view.getId() == R.id.resetButton) {
            boardModel.reset = 1;
            boardView.invalidate();
        }
        //SIZE DOWN BUTTON
        if(view.getId() == R.id.smallerButton){
            boardModel.smallVis = View.VISIBLE;
            if(boardModel.gridSize==4){
                boardModel.smallVis = View.INVISIBLE;
            }
            if(boardModel.gridSize>=4) {
                boardModel.reDraw = -1;
                boardModel.reset = 1;
                boardView.invalidate();
            }
            view.setVisibility(boardModel.smallVis);
        }
        //SIZE UP BUTTON
        if(view.getId() == R.id.largerButton){
            boardModel.smallVis = View.VISIBLE;
            if(boardModel.gridSize==9){
                boardModel.bigVis = View.INVISIBLE;
            }
            if(boardModel.gridSize < 10) {
                boardModel.reDraw = 1;
                boardModel.reset = 1;
                boardView.invalidate();
            }
            view.setVisibility(boardModel.bigVis);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
            boardModel.reset = -1;
            boardView.invalidate();
        }
        return false;
    }
}
