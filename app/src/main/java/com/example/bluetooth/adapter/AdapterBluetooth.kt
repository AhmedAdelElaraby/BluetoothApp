package com.example.bluetooth.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.Model.DataBlut
import com.example.bluetooth.databinding.ItemBinding



class AdapterBluetooth: RecyclerView.Adapter<AdapterBluetooth.ForYouHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForYouHolder {
        return ForYouHolder(ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ForYouHolder, position: Int) {
        holder.binding.name.text=differ.currentList[position].name
        holder.binding.address.text=differ.currentList[position].address
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ForYouHolder(var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<DataBlut>(){
        override fun areItemsTheSame(oldItem: DataBlut, newItem: DataBlut): Boolean {
            return  oldItem.name == newItem.name

        }


        override fun areContentsTheSame(oldItem: DataBlut, newItem: DataBlut): Boolean {
            return oldItem == newItem

        }

    }
   val differ = AsyncListDiffer(this,differCallback)





}