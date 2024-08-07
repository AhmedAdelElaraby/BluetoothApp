package com.example.bluetooth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bluetooth.Model.DataBlut

class ViewModel ():ViewModel() {


    private val data:MutableLiveData<List<DataBlut>> =MutableLiveData()
    val BulData:LiveData<List<DataBlut>> = data

    fun getData(dataBlut: DataBlut){

        data.value= listOf(dataBlut)



    }



}