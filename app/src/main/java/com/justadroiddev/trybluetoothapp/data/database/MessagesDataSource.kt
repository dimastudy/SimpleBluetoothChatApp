package com.justadroiddev.trybluetoothapp.data.database

import android.util.Log
import com.justadroiddev.trybluetoothapp.data.MessageEntityToDataMapper
import javax.inject.Inject

class MessagesDataSource @Inject constructor(
    private val database: ChatDatabase,
    private val messageEntityToDataMapper: MessageEntityToDataMapper
) : CacheDataSource {
    override suspend fun saveData(message: MessageEntity) {
        database.dao().saveMessage(message)
        Log.e("DataSource", "Saved from ${message.fromUser} to ${message.toUser}")
    }

    override suspend fun removeData(message: MessageEntity) {
        database.dao().removeMessage(message)
    }

    override fun updateMessage(messageEntity: MessageEntity) {
        database.dao().updateMessage(messageEntity)
    }

    override fun getMessagesToUser(from: String, to: String) : List<MessageEntity> =
        database.dao().getMessagesWithUser(from, to)

    override fun getMessagesFromUser(from: String, to: String): List<MessageEntity> =
        database.dao().getMessagesFromUser(from, to)

}