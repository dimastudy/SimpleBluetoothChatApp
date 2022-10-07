package com.justadroiddev.trybluetoothapp.data

import android.util.Log
import com.justadroiddev.trybluetoothapp.data.database.CacheDataSource
import com.justadroiddev.trybluetoothapp.domain.Repository
import com.justadroiddev.trybluetoothapp.presentation.ChatCallback
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val bluetoothService: BluetoothService,
    private val messageToEntityMapper: MessageToEntityMapper,
    private val entityToMessageMapper: MessageEntityToDataMapper
) : Repository {
    override suspend fun saveMessage(message: Message) {
        cacheDataSource.saveData(message.map(messageToEntityMapper))
    }

    override suspend fun deleteMessage(message: Message) {
        cacheDataSource.removeData(message.map(messageToEntityMapper))
    }

    override suspend fun updateMessage(message: Message) {
        cacheDataSource.updateMessage(message.map(messageToEntityMapper))
    }

    override fun getMessages(from: String, to: String): List<Message> {
        val messagesToUser = cacheDataSource.getMessagesToUser(from, to).map {
            it.map(entityToMessageMapper)
        }
        val messagesFromUser = cacheDataSource.getMessagesFromUser(from, to).map {
            it.map(entityToMessageMapper)
        }
        val messages = ArrayList<Message>().apply {
            addAll(messagesToUser)
            addAll(messagesFromUser)
            sortBy {
                it.id
            }
        }
        return messages
    }

    override suspend fun doServerWork(colorClient: Int, chatCallback: ChatCallback) {
        bluetoothService.doServerWork(colorClient, chatCallback)
    }

    override suspend fun doClientWork(colorClient: Int, user: User?, chatCallback: ChatCallback) {
        bluetoothService.doClientWork(colorClient, user, chatCallback)
    }

    override suspend fun sendMessage(
        user: User?,
        message: String,
        color: Int,
        updateListMessages: (message: Message) -> Unit,
        idDelete: Int
    ) {
        bluetoothService.sendMessage(user, message, color, updateListMessages, idDelete)
    }


}