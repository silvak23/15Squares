package com.example.a15squares;


import android.view.MotionEvent;
import android.view.View;

public class BoardController implements View.OnClickListener{


    //private instance variable
    private BoardView boardView;
    private BoardModel boardModel;

    //constructor
    BoardController(BoardView BoardViewIn) {
        boardView = BoardViewIn;
        boardModel = boardView.getBoardModel();
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.resetButton) {
            boardModel.reset = 1;
            boardView.invalidate();
        }
        if(view.getId() == R.id.smallerButton){
            if(boardModel.gridSize==4){
                view.setVisibility(View.INVISIBLE);
            }
            if(boardModel.gridSize>=4) {
                boardModel.reDraw = -1;
                boardModel.reset = 1;
                boardView.invalidate();
            }
        }
        if(view.getId() == R.id.largerButton){
            if(boardModel.gridSize==9){
                view.setVisibility(View.INVISIBLE);
            }
            if(boardModel.gridSize < 10) {
                boardModel.reDraw = 1;
                boardModel.reset = 1;
                boardView.invalidate();
            }
        }
        if(view.getId() == R.id.cheatButton){
            boardModel.reset = -1;
            boardView.invalidate();
        }
    }
}
