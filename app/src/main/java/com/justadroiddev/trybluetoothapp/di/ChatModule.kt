package com.justadroiddev.trybluetoothapp.di

import android.content.Context
import com.justadroiddev.trybluetoothapp.data.*
import com.justadroiddev.trybluetoothapp.data.database.CacheDataSource
import com.justadroiddev.trybluetoothapp.data.database.ChatDatabase
import com.justadroiddev.trybluetoothapp.data.database.MessagesDataSource
import com.justadroiddev.trybluetoothapp.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ChatModule {

    @Binds
    @Singleton
    abstract fun provideChatRepository(
        repository: ChatRepository
    ) : Repository



}