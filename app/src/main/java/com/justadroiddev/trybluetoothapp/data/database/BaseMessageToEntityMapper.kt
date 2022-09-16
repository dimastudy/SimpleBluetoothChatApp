package com.justadroiddev.trybluetoothapp.data.database

import com.justadroiddev.trybluetoothapp.data.MessageToEntityMapper

class BaseMessageToEntityMapper : MessageToEntityMapper {
    override fun map(
        id: Int,
        message: String,
        fromUser: String,
        toUser: String,
        userColor: Int
    ): MessageEntity = MessageEntity(id, message, fromUser, toUser, userColor)
}