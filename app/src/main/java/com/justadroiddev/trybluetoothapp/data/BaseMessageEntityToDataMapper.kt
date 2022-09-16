package com.justadroiddev.trybluetoothapp.data

class BaseMessageEntityToDataMapper : MessageEntityToDataMapper{
    override fun map(
        id: Int,
        message: String,
        fromUser: String,
        toUser: String,
        userColor: Int
    ): Message = Message(id, message, fromUser, toUser, userColor)
}