package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        textViewPlayer1 = findViewById( R.id.text_view_p1 );
        textViewPlayer2 = findViewById( R.id.text_view_p2 );

        for (int horizontal = 0; horizontal < 3; horizontal++) {
            for (int vertical = 0; vertical < 3; vertical++) {
                String buttonID = "button_" + horizontal + vertical;
                int resID = getResources().getIdentifier( buttonID, "id", getPackageName() );
                buttons[horizontal][vertical] = findViewById( resID );
                buttons[horizontal][vertical].setOnClickListener( this );
            }
        }

        Button buttonReset = findViewById( R.id.button_reset );
        buttonReset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        } );
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals( "" )) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText( "X" );
        } else {
            ((Button) v).setText( "O" );
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int horizontal = 0; horizontal < 3; horizontal++) {
            for (int vertical = 0; vertical < 3; vertical++) {
                field[vertical][horizontal] = buttons[vertical][horizontal].getText().toString();
            }
        }

        for (int horizontal = 0; horizontal < 3; horizontal++) {
            if (field[horizontal][0].equals( field[horizontal][1] )
                    && field[horizontal][0].equals( field[horizontal][2] )
                    && !field[horizontal][0].equals( "" )) {
                return true;
            }
        }

        for (int vertical = 0; vertical < 3; vertical++) {
            if (field[0][vertical].equals( field[1][vertical] )
                    && field[0][vertical].equals( field[2][vertical] )
                    && !field[0][vertical].equals( "" )) {
                return true;
            }
        }

        if (field[0][0].equals( field[1][1] )
                && field[0][0].equals( field[2][2] )
                && !field[0][0].equals( "" )) {
            return true;
        }

        if (field[0][2].equals( field[1][1] )
                && field[0][2].equals( field[2][0] )
                && !field[0][2].equals( "" )) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText( this, "Player 1 wins!", Toast.LENGTH_SHORT ).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText( this, "Player 2 wins!", Toast.LENGTH_SHORT ).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText( this, "This is a draw!!", Toast.LENGTH_LONG ).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText( "Player 1: " + player1Points );
        textViewPlayer2.setText( "Player 2: " + player2Points );
    }

    private void resetBoard() {
        for (int horizontal = 0; horizontal < 3; horizontal++) {
            for (int vertical = 0; vertical < 3; vertical++) {
                buttons[horizontal][vertical].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );

        outState.putInt( "roundCount", roundCount );
        outState.putInt( "player1Points", player1Points );
        outState.putInt( "player2Points", player2Points );
        outState.putBoolean( "player1Turn", player1Turn );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState( savedInstanceState );

        roundCount = savedInstanceState.getInt( "roundCount" );
        player1Points = savedInstanceState.getInt( "player1Points" );
        player2Points = savedInstanceState.getInt( "player2Points" );
        player1Turn = savedInstanceState.getBoolean( "player1Turn" );

    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }
}
