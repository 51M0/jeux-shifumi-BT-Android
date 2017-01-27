package com.example.a51m0.applibt;

import android.util.Log;
import android.view.View;

import java.nio.charset.Charset;

/**
 * Created by 51M0 on 23/01/2017.
 */

public class Ecouteur_jeux_online  implements View.OnClickListener {
    Traitement t;
    GameOnline g;

    public Ecouteur_jeux_online(Traitement t, GameOnline g) {
        this.t = t;
        this.g = g;
    }

    @Override
    public void onClick(View v) {

        if(!g.J1){
            Log.d("réussi click", "onClick: ");
            int aEnvoyer=1000;

            switch (v.getId()) {
                case R.id.papier:
                    g.P1 = 1;
                    aEnvoyer=1;
                    break;
                case R.id.pierre:
                    g.P1 = 2;
                    aEnvoyer=2;
                    break;
                case R.id.ciseaux:
                    g.P1 = 3;
                    aEnvoyer=3;
                    break;
            }
            byte[] bytes =Integer.toString(aEnvoyer).getBytes(Charset.defaultCharset());
            g.mBluetoothConnection.write(bytes);
            g.J1=true;
            synchronized (t){
                t.notify();
            }
        }
    }
}
