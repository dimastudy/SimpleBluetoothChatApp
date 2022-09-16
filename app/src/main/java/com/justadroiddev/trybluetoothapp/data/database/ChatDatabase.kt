package com.justadroiddev.trybluetoothapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun dao() : ChatDao

    companion object {
        lateinit var INSTANCE: ChatDatabase
        fun getChatDatabase(context: Context): ChatDatabase {
            if (!::INSTANCE.isInitialized) {
               INSTANCE = Room.databaseBuilder(
                    context,
                    ChatDatabase::class.java,
                    "chat_db"
                ).build()
            }
            return INSTANCE
        }
    }

}