package com.example.a51m0.applibt;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.Charset;

/**
 * Created by 51M0 on 22/01/2017.
 */

public class EcouteurSend implements View.OnClickListener {
    Activity a;
   EditText etSend;
    BluetoothConnectionService mBluetoothConnection;

    public EcouteurSend(Activity a,EditText etSend) {
        this.a = a;
        this.etSend=etSend;
        mBluetoothConnection= mBluetoothConnection.getInstance(a);

    }

    @Override
    public void onClick(View v) {
        Log.d("sending", "onClick: ");
            byte[] bytes = etSend.getText().toString().getBytes(Charset.defaultCharset());
            mBluetoothConnection.write(bytes);
            etSend.setText("");


    }
}
