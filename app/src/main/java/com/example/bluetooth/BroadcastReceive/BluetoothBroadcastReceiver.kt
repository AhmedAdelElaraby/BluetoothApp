package com.example.bluetooth.BroadcastReceive

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bluetooth.Utils.BluetoothDataListener


class BluetoothBroadcastReceiver(private val listener: BluetoothDataListener): BroadcastReceiver() {


    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {


        val action: String? = intent?.action
        if (BluetoothDevice.ACTION_FOUND == action) {
            // جهاز جديد تم اكتشافه
            Log.d("merna","One")
            val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            val deviceName = device!!.name
            val deviceAddress = device.address // MAC address
            if (deviceName != null && deviceAddress != null) {

                listener.onDeviceFound(deviceName, deviceAddress)
            }
        }

    }
    }
