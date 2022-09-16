package com.justadroiddev.trybluetoothapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.data.MessageEntityToDataMapper


@Entity(tableName = "messages_table")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String,
    val fromUser: String,
    val toUser: String,
    val userColor: Int
) {
    fun map(mapper: MessageEntityToDataMapper) : Message = mapper.map(id, message, fromUser, toUser, userColor)
}