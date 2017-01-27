package com.example.a51m0.applibt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by 51M0 on 22/01/2017.
 */

public class Chat extends Activity {

    TextView chatview;
    Button send;
    EditText chat;
    StringBuilder message;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    BluetoothDevice mBTDevice;
    BluetoothConnectionService mBluetoothConnection;
    private static final String TAG="on chat";

    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        mBTDevice = i.getExtras().getParcelable("btdevices");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        chatview = (TextView) (this.findViewById(R.id.ChatView));
        send = (Button) (this.findViewById(R.id.send));
        chat = (EditText) (this.findViewById(R.id.Chat));
        message = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRecevier, new IntentFilter("incoming message"));
        // send.setOnClickListener(new EcouteurSend(this,chat));
        mBluetoothConnection = mBluetoothConnection.getInstance(this);
        startConnection();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] bytes = chat.getText().toString().getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
                chat.setText("");
            }
        });
    }

    BroadcastReceiver mRecevier =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("on receiev", "recu");
            String text=intent.getStringExtra("the message");
            message.append(text +"\n");
            chatview.setText(message);

        }

    };
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
