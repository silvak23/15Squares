package com.example.a15squares;


import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

/**
 * BoardController
 *      The listener for most of the actions, mainly the buttons, on the board
 *
 * @author **** Kamalii Silva ****
 * @version **** 24 September 2021 ****
 */
public class BoardController implements View.OnClickListener, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

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
     * OnClick method for the button in the surface view
     * @param view that we will look at
     */
    @Override
    public void onClick(View view) {
        //RESET BUTTON
        if(view.getId() == R.id.resetButton) {
            boardModel.reset = 1;
            boardView.invalidate();
        }
    }

    /**
     * OnTouch
     *      IF YOU TOUCH THE TITLE BAR, IT SOLVES THE PUZZLE FOR YOU
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
            boardModel.reset = -1;
            boardView.invalidate();
            view.performClick();
            return true;
        }
        return false;
    }

    /**
     * onProgressChanged
     * @param seekBar which we are using
     * @param i progress
     * @param b info that it was changed
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //SIZE DOWN BUTTON
        if(i < boardModel.gridSize){
            boardModel.reDraw = -1;
            boardModel.reset = 1;
            boardView.invalidate();
        }
        //SIZE UP BUTTON
        if(i > boardModel.gridSize) {
            boardModel.reDraw = 1;
            boardModel.reset = 1;
            boardView.invalidate();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
