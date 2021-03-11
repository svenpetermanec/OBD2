package com.example.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    ArrayAdapter<String> arrayAdapter;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        l1 = (ListView)findViewById(R.id.listView);
        findPairedDevices();
    }

    public void findPairedDevices(){
        int index = 0;
        Set<BluetoothDevice> bluetoothSet = bluetoothAdapter.getBondedDevices();
        String[] str = new String[bluetoothSet.size()];

        if(bluetoothSet.size() > 0){
            for(BluetoothDevice device: bluetoothSet){
                str[index] = device.getName();
                index++;
            }
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, str);
            l1.setAdapter(arrayAdapter);
        }
    }

    public void on(View v){
        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Bluetooth not supported", Toast.LENGTH_SHORT).show();
        }else{
            if(!bluetoothAdapter.isEnabled()){
                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(i, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Bluetooth on", Toast.LENGTH_SHORT).show();
            }
        }
    }
}