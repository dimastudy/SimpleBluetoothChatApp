package com.justadroiddev.trybluetoothapp.data

import com.justadroiddev.trybluetoothapp.data.database.MessageEntity

interface MessageToEntityMapper {

    fun map(
        id: Int,
        message: String,
        fromUser: String,
        toUser: String,
        userColor: Int
    ) : MessageEntity

}