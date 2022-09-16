package com.justadroiddev.trybluetoothapp.presentation

interface ChatCallback {

    fun updateStatus(status: StateBluetoothUi)

    fun updateChatMessages(from: String, to: String)

}