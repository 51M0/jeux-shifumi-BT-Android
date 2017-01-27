package com.example.a51m0.applibt;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;

/**
 * Created by 51M0 on 28/12/2016.
 */

public class EcouteurVisible implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
      //  startActivity(discoverableIntent);
    }
}
