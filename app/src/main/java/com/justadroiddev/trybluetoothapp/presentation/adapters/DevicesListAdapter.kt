package com.justadroiddev.trybluetoothapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justadroiddev.trybluetoothapp.R
import com.justadroiddev.trybluetoothapp.data.User
import com.justadroiddev.trybluetoothapp.databinding.ItemDeviceBinding

class DevicesListAdapter(
    private val clickListener: DeviceClickListener
) : ListAdapter<User, DevicesListAdapter.DeviceViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false))
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = getItem(position)
        holder.bind(device)
        holder.itemView.setOnClickListener {
            clickListener.onClick(device)
        }
    }


    class DeviceViewHolder(private val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(device: User){
            binding.userNameText.text = device.name
        }

    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

        }
    }

    class DeviceClickListener(private val click : (device: User) -> Unit){
        fun onClick(device: User) = click(device)
    }

}