package com.justadroiddev.trybluetoothapp.data

import com.justadroiddev.trybluetoothapp.presentation.ChatCallback
import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi


interface BluetoothService {

    suspend fun doServerWork(colorClient: Int, chatCallback: ChatCallback)

    suspend fun doClientWork(colorClient: Int, user: User?,chatCallback: ChatCallback)

    suspend fun sendMessage(user: User?, message: String, color: Int, updateListMessages: (message: Message) -> Unit, idDelete: Int)

}