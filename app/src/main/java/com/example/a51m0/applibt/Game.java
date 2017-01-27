package com.example.a51m0.applibt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by 51M0 on 09/01/2017.
 */


public class Game extends Activity {
    private ImageView pierre,papier,ciseaux,waiting;
    private TextView score,scoreoppenet,textscore;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

    }
    protected void onStart() {
        super.onStart();
        pierre=(ImageView) this.findViewById(R.id.pierre);
        papier=(ImageView) this.findViewById(R.id.papier);
        ciseaux=(ImageView) this.findViewById(R.id.ciseaux);
        pierre.setImageResource(R.drawable.pierre);
        papier.setImageResource(R.drawable.papier);
        ciseaux.setImageResource(R.drawable.ciseaux);
        waiting=(ImageView) this.findViewById(R.id.waiting);
        score=(TextView) this.findViewById(R.id.score);
        scoreoppenet=(TextView)this.findViewById(R.id.score_oppenent);
        textscore=(TextView) this.findViewById(R.id.text_score);
        score.setText("0");
        scoreoppenet.setText("0");
        this.papier.setOnClickListener(new Ecouteur_jeux(this));
        this.pierre.setOnClickListener(new Ecouteur_jeux(this));
        this.ciseaux.setOnClickListener(new Ecouteur_jeux(this));
    }
    public void setWaiting(int i){
        switch (i){
            case 1:
                waiting.setImageResource(R.drawable.papier);
                break;
            case 2:
                waiting.setImageResource(R.drawable.pierre);
                break;
            case 3:
                waiting.setImageResource(R.drawable.ciseaux);
                break;

        }
    }
 public void setTextscore (String s){
        textscore.setText(s);
    }
    public void addscore (){
        int s=(int) Integer.parseInt(score.getText().toString());
        s++;
        score.setText(String.valueOf(s));
    }
    public void addscoreopponent(){
        int s=(int) Integer.parseInt(scoreoppenet.getText().toString());
        s++;
        scoreoppenet.setText(String.valueOf(s));
    }

}


