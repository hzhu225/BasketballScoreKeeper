package com.cms.basketballscorekeeper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    public Stack<Integer> scoreStack;      //use a stack to store scores to enable Undo. Team A scores are positive. Team B scores are negative.
    private TextView teamAScore;
    private TextView teamBScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreStack = new Stack<>();
        teamAScore = findViewById(R.id.teamAScoreText);
        teamBScore = findViewById(R.id.teamBScoreText);
        updateScore();
    }

    public void APlus1_Click(View view)             //To add 1 score for Team A, push 1 into scoreStack.
    {
        scoreStack.push(1);
        updateScore();
    }

    public void APlus2_Click(View view)
    {
        scoreStack.push(2);
        updateScore();
    }

    public void APlus3_Click(View view)
    {
        scoreStack.push(3);
        updateScore();
    }

    public void BPlus1_Click(View view)             //To add 1 score for Team B, push -1 into scoreStack.
    {
        scoreStack.push(-1);
        updateScore();
    }

    public void BPlus2_Click(View view)
    {
        scoreStack.push(-2);
        updateScore();
    }

    public void BPlus3_Click(View view)
    {
        scoreStack.push(-3);
        updateScore();
    }

    public void undo_Click(View view)              //user can click undo button to go back
    {
        //build an alert dialog, ask user to confirm
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(MainActivity.this);
        confirmDialog.setTitle(R.string.undoAlert_title);
        confirmDialog.setMessage(R.string.undoAlert_message);

        confirmDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String message;
                        if (!scoreStack.isEmpty())
                        {
                            int lastScore = scoreStack.pop();       //pop last score and check its value
                            if (lastScore > 0)
                            {
                                message = "Undo: Team A +" + lastScore;
                            }
                            else
                            {
                                message = "Undo: Team B +" + Math.abs(lastScore);
                            }
                            updateScore();
                        }
                        else                                      //if scoreStack is empty, there is no score yet. So cannot undo.
                        {
                            message = "No score has been recorded.";
                        }
                        Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 25);
                        toast.show();
                    }
                });

        confirmDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                });

        confirmDialog.show();
    }



    public void reset_Click(View view)           //when reset, clear the scoreStack
    {
        //build an alert dialog, ask user to confirm
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(MainActivity.this);
        confirmDialog.setTitle(R.string.resetAlert_title);
        confirmDialog.setMessage(R.string.resetAlert_message);

        confirmDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        scoreStack.clear();
                        updateScore();
                    }
                });

        confirmDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                });

        confirmDialog.show();

    }


    @SuppressLint("SetTextI18n")
    private void updateScore()                  //calculate the sum score for each team and show them
    {
        int score_A = 0;
        int score_B = 0;

        if (!scoreStack.isEmpty())
        {
            for (Integer s : scoreStack)           //foreach Integer s in scoreStack
            {
                if (s > 0)
                {
                    score_A += s;
                }
                else
                {
                    score_B += s;
                }
            }
        }
        teamAScore.setText(Integer.toString(score_A));
        teamBScore.setText(Integer.toString(Math.abs(score_B)));   //Team B scores are recorded as negative numbers. Show its absolute value.
    }



}

