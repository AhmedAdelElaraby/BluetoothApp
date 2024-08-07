package com.example.bluetooth.Utils

interface BluetoothDataListener {
    fun onDeviceFound(deviceName: String, deviceAddress: String)

}