package com.example.a51m0.applibt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by 51M0 on 09/01/2017.
 */


public class GameOnline extends Activity {
    public ImageView pierre,papier,ciseaux;
    public ImageView waiting;
    public TextView score,scoreoppenet,textscore;
    public boolean J1=false,J2=false;
    private StringBuilder message;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    BluetoothDevice mBTDevice;
    BluetoothConnectionService mBluetoothConnection;
    Traitement traitement;
    private static final String TAG="on game";
    public int P1,P2,Round;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBluetoothConnection!=null){
            mBluetoothConnection.cancel();
        }
        Round=0;
        setContentView(R.layout.game);
        Intent i = getIntent();
        mBTDevice = i.getExtras().getParcelable("btdevices");
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
        mBluetoothConnection = mBluetoothConnection.getInstance(this);
        startConnection();



    }
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: start");
        if (traitement!= null && traitement.isAlive()){
            traitement.interrupt();
        }
        traitement= new Traitement(this);
        papier.setOnClickListener(new Ecouteur_jeux_online(traitement,this));
        pierre.setOnClickListener(new Ecouteur_jeux_online(traitement,this));
        ciseaux.setOnClickListener(new Ecouteur_jeux_online(traitement,this));
        BroadcastReceiver mRecevier =new Reciever(this,traitement) ;
        LocalBroadcastManager.getInstance(this).registerReceiver(mRecevier, new IntentFilter("incoming message"));

        waiting.setImageResource(R.drawable.hourglass);
        traitement.start();

   }

    @Override
    protected void onRestart() {
        Log.d("restart","complete");
        if (traitement.isAlive()){
            traitement.interrupt();
        }
       /* traitement= new Traitement(this);
        traitement.start();*/
        super.onRestart();
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

    public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }


    /**
     * starting chat service method
     */

    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device,uuid);
    }



}
