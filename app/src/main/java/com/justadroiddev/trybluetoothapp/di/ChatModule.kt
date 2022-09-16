package com.justadroiddev.trybluetoothapp.di

import android.content.Context
import com.justadroiddev.trybluetoothapp.data.*
import com.justadroiddev.trybluetoothapp.data.database.CacheDataSource
import com.justadroiddev.trybluetoothapp.data.database.ChatDatabase
import com.justadroiddev.trybluetoothapp.data.database.MessagesDataSource
import com.justadroiddev.trybluetoothapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context) : ResourceManager =
        BaseResourceManager(context)

    @Provides
    @Singleton
    fun provideBluetoothService(
        @ApplicationContext context: Context,
        stateBluetoothDataToUiMapper: StateBluetoothDataToUiMapper
    ) : BluetoothService =
        BaseBluetoothService(context, stateBluetoothDataToUiMapper)

    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context) : ChatDatabase =
        ChatDatabase.getChatDatabase(context)

    @Provides
    @Singleton
    fun provideCacheDataSource(
        database: ChatDatabase,
        entityToDataMapper: MessageEntityToDataMapper
    ) : CacheDataSource =
        MessagesDataSource(database, entityToDataMapper)

    @Provides
    @Singleton
    fun provideChatRepository(
        cacheDataSource: CacheDataSource,
        bluetoothService: BluetoothService,
        messageToEntityMapper: MessageToEntityMapper,
        entityToDataMapper: MessageEntityToDataMapper
    ) : Repository =
        ChatRepository(cacheDataSource, bluetoothService, messageToEntityMapper, entityToDataMapper)




}