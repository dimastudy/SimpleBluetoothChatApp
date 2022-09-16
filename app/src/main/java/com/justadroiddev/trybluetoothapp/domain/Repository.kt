package com.justadroiddev.trybluetoothapp.domain

import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.data.User
import com.justadroiddev.trybluetoothapp.presentation.ChatCallback

interface Repository {

    suspend fun saveMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun updateMessage(message: Message)

    fun getMessages(from: String, to: String) : List<Message>

    suspend fun doServerWork(colorClient: Int, chatCallback: ChatCallback)

    suspend fun doClientWork(colorClient: Int, user: User?, chatCallback: ChatCallback)

    suspend fun sendMessage(user: User?, message: String, color: Int, updateListMessages: (message: Message) -> Unit, idDelete: Int)

}