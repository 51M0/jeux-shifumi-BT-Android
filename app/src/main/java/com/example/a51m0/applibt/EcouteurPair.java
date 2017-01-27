package com.example.a51m0.applibt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by 51M0 on 18/01/2017.
 */

public class EcouteurPair implements AdapterView.OnItemClickListener{
    private static final String TAG = "DeviceListActivity";
    Activity main;
    List<BluetoothDevice> listbt;
    BluetoothAdapter btAdapter;
    BluetoothConnectionService mBluetoothConnection;

    public EcouteurPair(Activity main, List<BluetoothDevice> listbt, BluetoothAdapter btAdapter) {
        this.main = main;
        this.listbt = listbt;
        this.btAdapter = btAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        btAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: You Clicked on a device.");
        String deviceName = listbt.get(position).getName();
        String deviceAddress = listbt.get(position).getAddress();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);


        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "Trying to pair with " + deviceName);
            Intent result = new Intent();
            result.putExtra("btDevice", listbt.get(position));
            main.setResult(main.RESULT_OK, result);
            main.finish();
           /* listbt.get(position).createBond();
           mBluetoothConnection = new BluetoothConnectionService(main);*/
        }
    }
}
