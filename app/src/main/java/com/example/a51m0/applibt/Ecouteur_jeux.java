package com.example.a51m0.applibt;

import android.app.Activity;
import android.view.View;

import java.util.Random;

/**
 * Created by 51M0 on 09/01/2017.
 */

public class Ecouteur_jeux implements View.OnClickListener {
    Game g;
    int clique, opponent;


    public Ecouteur_jeux(Game g) {
        this.g = g;
    }

    @Override
    public void onClick(View v) {
        Random r = new Random();
        opponent = (int) r.nextInt(3) + 1;
        g.setWaiting(opponent);
        switch (v.getId()) {
            case R.id.papier:
                clique = 1;
                break;
            case R.id.pierre:
                clique = 2;
                break;
            case R.id.ciseaux:
                clique = 3;
                break;
        }
        this.traitement();
    }

    public void traitement() {
        if (clique == opponent) {
            g.setTextscore("draw");
        }
        switch (clique) {
            case 1: {
                if (opponent == 2) {
                    g.setTextscore("win");
                    g.addscore();
                }
                if (opponent == 3) {
                    g.setTextscore("lose");
                    g.addscoreopponent();
                }

            }
            break;
            case 2: {
                if (opponent == 3) {
                    g.setTextscore("win");
                    g.addscore();
                }
                if (opponent == 1) {
                    g.setTextscore("lose");
                    g.addscoreopponent();
                }
            }
            break;
            case 3: {
                if (opponent == 1) {
                    g.setTextscore("win");
                    g.addscore();
                }
                if (opponent == 2) {
                    g.setTextscore("lose");
                    g.addscoreopponent();
                }
            }
            break;

        }
    }
}
