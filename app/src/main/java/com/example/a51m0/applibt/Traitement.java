package com.example.a51m0.applibt;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 51M0 on 23/01/2017.
 */

public class Traitement extends Thread {
GameOnline gameOnline;

    public Traitement(GameOnline gameOnline) {
        this.gameOnline = gameOnline;
    }
    public synchronized void run() {
        super.run();

        gameOnline.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameOnline.waiting.setImageResource(R.drawable.hourglass);;
                gameOnline.textscore.setText("Round"+gameOnline.Round++);

            }}
        );
        while (!gameOnline.J1 || !gameOnline.J2){
            try {
                Log.d("thread", "run: ");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameOnline.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameOnline.setWaiting(gameOnline.P2);
                gameOnline.papier.setVisibility(View.VISIBLE);
                gameOnline.pierre.setVisibility(View.VISIBLE);
                gameOnline.ciseaux.setVisibility(View.VISIBLE);
            }}
        );

        partie();

        gameOnline.J1=false;
        gameOnline.J2=false;
        Log.d("thread", "end: ");
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameOnline.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameOnline.onStart();
            }}
        );





    }

    public void setTextscore (final String s){
        gameOnline.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameOnline.textscore.setText(s);
            }}
        );

    }
    public void addscore (){
        int s=(int) Integer.parseInt(gameOnline.score.getText().toString());
        s++;
        final int finalS1 = s;
        gameOnline.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameOnline.score.setText(String.valueOf(finalS1));
            }}
        );

    }
    public void addscoreopponent(){
        int s=(int) Integer.parseInt(gameOnline.scoreoppenet.getText().toString());
        s++;
        final int finalS2 = s;
        gameOnline.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameOnline.scoreoppenet.setText(String.valueOf(finalS2));
            }}
        );

    }
    public void partie() {
        Log.d("partie", "jouer");
        if (gameOnline.P1 == gameOnline.P2) {
            setTextscore("draw");
        }
        switch (gameOnline.P1) {
            case 1: {
                if (gameOnline.P2 == 2) {
                    setTextscore("win");
                    addscore();
                }
                if (gameOnline.P2 == 3) {
                    setTextscore("lose");
                    addscoreopponent();
                }

            }
            break;
            case 2: {
                if (gameOnline.P2 == 3) {
                    setTextscore("win");
                    addscore();
                }
                if (gameOnline.P2 == 1) {
                    setTextscore("lose");
                    addscoreopponent();
                }
            }
            break;
            case 3: {
                if (gameOnline.P2 == 1) {
                    setTextscore("win");
                    addscore();
                }
                if (gameOnline.P2 == 2) {
                    setTextscore("lose");
                    addscoreopponent();
                }
            }
            break;

        }

    }

}
