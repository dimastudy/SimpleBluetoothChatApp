package com.justadroiddev.trybluetoothapp.presentation

import com.justadroiddev.trybluetoothapp.data.Message

sealed class MessagesStatus() {

    object Empty : MessagesStatus()

    class MessageExists(val data: List<Message>) : MessagesStatus()

}