package com.cheshianhung.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int GAME_TIME = 30;
    public static final int MAX = 100;
    public static final int MIN = -100;

    protected int answer;
    protected int correctNum;
    protected int questionNum;
    protected TextView timer;
    protected TextView score;
    protected TextView result;
    protected TextView question;
    protected Button go;
    protected Button b1;
    protected Button b2;
    protected Button b3;
    protected Button b4;
    protected Button playAgain;
    protected Button[] btnGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);
        result = (TextView) findViewById(R.id.result);
        question = (TextView) findViewById(R.id.question);
        go = (Button) findViewById(R.id.go);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        playAgain = (Button) findViewById(R.id.playAgain);
        btnGroup = new Button[] {b1, b2, b3, b4};

        beginView();

    }

    public void go (View view) {

        playView();
        startTimer();
        changeQuestionText();

    }

    protected void startTimer (){
        new CountDownTimer(GAME_TIME * 1000 + 100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerText((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                String text = "Your score: " + score.getText().toString();

                timerText(0);

                for(int i = 0; i < btnGroup.length; i++)
                    btnGroup[i].setEnabled(false);

                playAgain.setVisibility(View.VISIBLE);

                result.setText(text);
            }

        }.start();

    }

    public void playAgain (View view) {

        beginView();

    }

    public void checkAnswer (View view) {

        Button thisBtn = (Button) view;

        if(Integer.parseInt(thisBtn.getText().toString()) == answer){
            correctNum++;
            result.setText("Correct!");
        }
        else
            result.setText("Wrong!");

        questionNum++;


        score.setText(String.valueOf(correctNum) + " / " + String.valueOf(questionNum));

        changeQuestionText();
    }

    protected void timerText(int time) {

        String text = String.valueOf(time) + " s";
        timer.setText(text);

    }

    protected int randomNum() {

        return (int)(Math.random() * MAX * 2 + MIN);

    }

    protected void changeQuestionText() {

        int a = randomNum();
        int b = randomNum();
        int temp[] = new int[4];
        String aStr = String.valueOf(a);
        String bStr = String.valueOf(b);
        answer = a + b;
        int correctIndex = (int) (Math.random() * 4);

        if(a < 0)
            aStr = "(" + aStr + ")";
        if(b < 0)
            bStr = "(" + bStr + ")";

        question.setText(aStr + " + " + bStr + " = ?");

        for(int i = 0; i < btnGroup.length; i++) {
            int num;

            do {
                num = (int) (Math.random() * 20 - 10 );
            }while(num == 0 || checkAry(num, temp, i));

            temp[i] = num;
            btnGroup[i].setText(String.valueOf(answer + num));
        }

        btnGroup[correctIndex].setText(String.valueOf(answer));

    }

    protected boolean checkAry(int numToCheck, int[] tempAry, int index) {

        boolean repeat = false;

        for(int i = 0; i < index; i++) {
            if (tempAry[i] == numToCheck)
                repeat = true;
        }

        return repeat;

    }

    protected void beginView() {

        go.setVisibility(View.VISIBLE);
        timer.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);

    }

    protected void playView() {

        go.setVisibility(View.INVISIBLE);
        timer.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        b4.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);

        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);

        score.setText("0 / 0");
        result.setText("");
        correctNum = 0;
        questionNum = 0;

    }

}
