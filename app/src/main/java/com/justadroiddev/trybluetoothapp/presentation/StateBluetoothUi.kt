package com.justadroiddev.trybluetoothapp.presentation

import com.justadroiddev.trybluetoothapp.data.Message

sealed class StateBluetoothUi {
    data class InfoMessage(val message: String, val canWrite: Boolean) : StateBluetoothUi()
    data class MessageReceivedUi(val message: Message) : StateBluetoothUi()
}