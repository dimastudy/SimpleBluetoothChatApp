package com.justadroiddev.trybluetoothapp.data.database

import com.justadroiddev.trybluetoothapp.data.Message

interface CacheDataSource {

    suspend fun saveData(message: MessageEntity)

    suspend fun removeData(message: MessageEntity)

    fun updateMessage(messageEntity: MessageEntity)

    fun getMessagesToUser(from: String, to: String) : List<MessageEntity>

    fun getMessagesFromUser(from: String, to: String) : List<MessageEntity>




}