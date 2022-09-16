package com.justadroiddev.trybluetoothapp.di

import com.justadroiddev.trybluetoothapp.data.*
import com.justadroiddev.trybluetoothapp.data.database.BaseMessageToEntityMapper
import com.justadroiddev.trybluetoothapp.presentation.BaseStateBluetoothDataToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Provides
    @Singleton
    fun provideMessageToEntityMapper() : MessageToEntityMapper =
        BaseMessageToEntityMapper()

    @Provides
    @Singleton
    fun provideMessageEntityToDataMapper() : MessageEntityToDataMapper =
        BaseMessageEntityToDataMapper()

    @Provides
    @Singleton
    fun provideStateBluetoothDataToUiMapper(resourceManager: ResourceManager) : StateBluetoothDataToUiMapper =
        BaseStateBluetoothDataToUiMapper(resourceManager)


}