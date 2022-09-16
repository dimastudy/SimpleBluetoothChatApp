package com.justadroiddev.trybluetoothapp.data

interface MessageEntityToDataMapper {

    fun map(
        id: Int,
        message: String,
        fromUser: String,
        toUser: String,
        userColor: Int
    ) : Message

}
