package com.justadroiddev.trybluetoothapp.presentation.adapters

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justadroiddev.trybluetoothapp.R
import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.databinding.ItemMessageBinding

class MessagesListAdapter(
    private val nameDevice: String,
    private val clickListener: ClickListener
) : ListAdapter<Message, MessagesListAdapter.MessagesViewHolder>(
    DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val binding = ItemMessageBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
        return MessagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, nameDevice)
        holder.itemView.setOnClickListener {
            clickListener.onClick(message, nameDevice)
        }
    }

    class MessagesViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(message: Message, nameDevice: String){
            binding.apply {
                if (message.fromUser == nameDevice){
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.END
                    }
                    cardContainer.layoutParams = params

                } else {
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.START
                    }
                    cardContainer.layoutParams = params
                }
                cardContainer.setCardBackgroundColor(message.userColor)
                textMessage.text = message.message
                userNameTv.text = message.fromUser
            }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem == newItem
        }
    }

    class ClickListener(private val clickListener : (message: Message, userName: String) -> Unit){
        fun onClick(message: Message, userName: String) = clickListener(message, userName)
    }

}