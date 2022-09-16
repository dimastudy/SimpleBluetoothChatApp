package com.justadroiddev.trybluetoothapp.data

import androidx.annotation.ColorRes
import com.justadroiddev.trybluetoothapp.data.database.MessageEntity

data class Message(
    val id: Int = 0,
    val message: String,
    val fromUser: String,
    val toUser: String,
    @ColorRes val userColor: Int
) {
    fun map(mapper: MessageToEntityMapper) : MessageEntity = mapper.map(id, message, fromUser, toUser, userColor)

    fun changeTextMessage(text: String) = Message(id, text, fromUser, toUser, userColor)

    fun changeUserColor(@ColorRes newColor: Int) = Message(id, message, fromUser, toUser, newColor)

    companion object {
        const val NEW_ADDED_MESSAGE = 0
    }

}