package com.example.a51m0.applibt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private final String TAG="Main activity";
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    Button pair,visible;
    Button offline;
    Button online;
    BluetoothDevice mBTDevice;
    BluetoothAdapter bluetoothAdapter ;
    BluetoothConnectionService    mBluetoothConnection;
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            }
        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.pair= (Button) this.findViewById(R.id.Pair_Button);
        this.visible=(Button) this.findViewById(R.id.visible);
        this.offline=(Button) this.findViewById(R.id.offline);
        this.online=(Button) this.findViewById(R.id.online);
        online.setVisibility(View.INVISIBLE);
    }




    @Override
    protected void onStart() {

        super.onStart();


         bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);


        }
        IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter1);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);
        this.offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I=new Intent(MainActivity.this,com.example.a51m0.applibt.Game.class);
                MainActivity.this.startActivityForResult(I, 2);


            }
        });

        this.visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }
        });
        this.pair.setOnClickListener(new EcouteurButtonPair(this));

        online.setOnClickListener( new EcouteurConnexionStart(this,MY_UUID_INSECURE,mBTDevice));

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== REQUEST_CODE_ENABLE_BLUETOOTH)
        {
        if (resultCode == RESULT_OK) {

            bluetoothAdapter.startDiscovery();

        } else {
            finish();
        }}
        else if(requestCode == 5){
            if (resultCode==RESULT_OK) {


                online.setVisibility(View.VISIBLE);
                mBTDevice = data.getExtras().getParcelable("btDevice");
                mBTDevice.createBond();
                // mBluetoothConnection = BluetoothConnectionService.getInstance(this);
                this.onRestart();

            }

        }
        else if(requestCode == 10){
            if (resultCode==RESULT_CANCELED) {




            }

        }

    }


    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        final String action = intent.getAction();

                        if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                            BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            //3 cases:
                            //case1: bonded already
                            if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                                Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                                //inside BroadcastReceiver4
                                mBTDevice = mDevice;
                            }
                            //case2: creating a bone
                            if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                                Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                            }
                            //case3: breaking a bond
                            if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                                Log.d(TAG, "BroadcastReceiver: BOND_NONE.");

                            }
            }
        }
    };
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver4);
       bluetoothAdapter.cancelDiscovery();
    }



}
