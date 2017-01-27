package com.example.a51m0.applibt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 51M0 on 05/10/2016.
 */

public class EcouteurButtonPair implements View.OnClickListener {
       MainActivity activity;
    public EcouteurButtonPair(MainActivity a) {
        this.activity=a;
    }



    @Override

    public void onClick(View V) {
        Intent I=new Intent(activity,com.example.a51m0.applibt.ListeDevice.class);
        activity.startActivityForResult(I, 5);

    }
}
