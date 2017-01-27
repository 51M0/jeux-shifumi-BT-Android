package com.example.a51m0.applibt;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.UUID;

/**
 * Created by 51M0 on 22/01/2017.
 */

public class EcouteurConnexionStart implements AdapterView.OnClickListener {
    private final String TAG= "Ecouteur boutton connexion";
    private Activity act;
    private UUID MY_UUID_INSECURE;
    private BluetoothDevice mBTDevice;
 //   private BluetoothConnectionService mBluetoothConnection;

    public EcouteurConnexionStart(Activity act, UUID MY_UUID_INSECURE, BluetoothDevice mBTDevice) {
        this.act = act;
       // this.MY_UUID_INSECURE = MY_UUID_INSECURE;
        this.mBTDevice = mBTDevice;
      //  this.mBluetoothConnection = mBluetoothConnection;
    }
    /*public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    /*
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device,uuid);
    }

*/
    @Override
    public void onClick(View v) {
       // startConnection();

        Intent I=new Intent(act,com.example.a51m0.applibt.GameOnline.class);
        I.putExtra("btdevices",mBTDevice);

        act.startActivityForResult(I, 10);
    }
}
