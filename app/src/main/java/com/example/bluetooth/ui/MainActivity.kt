package com.example.bluetooth.ui

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetooth.BroadcastReceive.BluetoothBroadcastReceiver
import com.example.bluetooth.Model.DataBlut
import com.example.bluetooth.R
import com.example.bluetooth.Utils.BluetoothDataListener
import com.example.bluetooth.adapter.AdapterBluetooth
import com.example.bluetooth.databinding.ActivityMainBinding
import java.util.ArrayList


class MainActivity : AppCompatActivity(), BluetoothDataListener {
// //   private val device_supports_bluetooth=DeviceSupportsBluetooth(this)
 private val REQUEST_ENABLE_BT = 1
    private val PERMISSION_REQUEST_CODE = 100
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    lateinit var binding:ActivityMainBinding
    val adaptter= AdapterBluetooth()
    lateinit var  BluetoothSearch : BluetoothBroadcastReceiver
     val array:ArrayList<DataBlut> = ArrayList()

    val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rec.apply {
            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
            adapter =adaptter
        }


    viewModel.BulData.observe(this){
        adaptter.differ.submitList(it)
        adaptter.notifyDataSetChanged()

    }




     checkPermissions()
    }

    private fun checkPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }



        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
        } else {
            initializeBluetooth()
        }
    }










    @SuppressLint("MissingPermission")
    private fun initializeBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        // متابعة عمل البلوتوث هنا


        BluetoothSearch= BluetoothBroadcastReceiver(this)

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(BluetoothSearch, filter)
        bluetoothAdapter.startDiscovery()


    }
//
//
//
//
//
//
//
//
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                initializeBluetooth()
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDeviceFound(deviceName: String, deviceAddress: String) {
//        Toast.makeText(this, "$deviceName + $deviceAddress", Toast.LENGTH_SHORT).show()
     val data = DataBlut(deviceName,deviceAddress)
      viewModel.getData(data)

    }


}