package com.justadroiddev.trybluetoothapp.data.database

import androidx.room.*
import com.justadroiddev.trybluetoothapp.data.Message

@Dao
interface ChatDao {

    @Query("select * from messages_table where fromUser = :from and toUser = :to")
    fun getMessagesWithUser(from: String, to: String) : List<MessageEntity>

    @Query("select * from messages_table where fromUser = :to and toUser = :from")
    fun getMessagesFromUser(from: String, to: String) : List<MessageEntity>

    @Insert
    suspend fun saveMessage(messageEntity: MessageEntity)

    @Delete
    suspend fun removeMessage(messageEntity: MessageEntity)

    @Update
    fun updateMessage(messageEntity: MessageEntity)

}