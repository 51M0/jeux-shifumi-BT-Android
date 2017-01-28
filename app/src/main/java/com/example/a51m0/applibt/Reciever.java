package com.example.a51m0.applibt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 51M0 on 23/01/2017.
 */

public class Reciever extends BroadcastReceiver   {
    GameOnline g;
    Traitement t;

    public Reciever(GameOnline g,Traitement t) {

        this.g = g;
        this.t=t;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!g.J2){
            g.textscore.setText("your turn");
            Log.d("on receiev", "recu");
            String text=intent.getStringExtra("the message");
            //message.append(text +"\n");
           Integer i=Integer.parseInt(text);
            g.P2=i;
            g.J2=true;
        }


        synchronized (t){
            t.notify();
        }

    }


}
