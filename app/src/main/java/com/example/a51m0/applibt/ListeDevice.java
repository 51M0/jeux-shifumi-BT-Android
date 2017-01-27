package com.example.a51m0.applibt;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 51M0 on 28/12/2016.
 */


public class ListeDevice extends Activity {
    private static final String TAG = "DeviceListActivity";

    private ArrayList<BluetoothDevice> listBt;
    private DeviceListAdapter adapterBt;
    private DeviceListAdapter bondedAdapterBt;
    private ArrayList<BluetoothDevice> listBtBonded;
    private ListView listViewNewBt;
    private ListView listViewBtBonded;

    private BluetoothAdapter mBluetoothAdapter;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BroadcastReceiver broadcastrecevierscan= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "recherche bt");
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)){

                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                if (device!=null) {

                    Log.d(TAG, device.getAddress());
                    if (device.getName()!=null)
                        Log.d(TAG, device.getName());
                    listBt.add(device);
                    adapterBt = new DeviceListAdapter(context, R.layout.device_adapter_view, listBt);
                    listViewNewBt.setAdapter(adapterBt);
                }
            }
        }
    };

    public void checkBTPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");

                permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");

            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkBTPermissions();
        super.onCreate(savedInstanceState);

        mBluetoothAdapter=  BluetoothAdapter.getDefaultAdapter();

        // Setup the window
        setContentView(R.layout.activity_device_list);
        listViewNewBt = (ListView) findViewById(R.id.new_devices);
        listViewBtBonded =(ListView) findViewById(R.id.paired_devices);

        listBt= new ArrayList<BluetoothDevice>();
        //inistialisation des periph deja existants
        listBtBonded = new ArrayList<BluetoothDevice>();
        listBtBonded.addAll(mBluetoothAdapter.getBondedDevices());
        bondedAdapterBt= new DeviceListAdapter(this, R.layout.device_adapter_view, listBtBonded);
        listViewBtBonded.setAdapter(bondedAdapterBt);





        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                    Log.d(TAG, "btnDiscover: Canceling discovery.");

                    //check BT permissions in manifest
                    checkBTPermissions();

                    mBluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(broadcastrecevierscan, discoverDevicesIntent);
                }
                if(!mBluetoothAdapter.isDiscovering()){

                    //check BT permissions in manifest
                    checkBTPermissions();

                    mBluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(broadcastrecevierscan, discoverDevicesIntent);
                }
            }
        });




        // ajouter la connexion
        listViewBtBonded.setOnItemClickListener(new EcouteurPair(this,listBtBonded,mBluetoothAdapter));
        listViewNewBt.setOnItemClickListener(new EcouteurPair(this,listBt,mBluetoothAdapter));







        }
    ;
}
