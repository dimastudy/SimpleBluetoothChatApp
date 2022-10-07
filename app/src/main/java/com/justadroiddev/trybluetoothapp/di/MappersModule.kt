package com.justadroiddev.trybluetoothapp.di

import com.justadroiddev.trybluetoothapp.data.*
import com.justadroiddev.trybluetoothapp.data.database.BaseMessageToEntityMapper
import com.justadroiddev.trybluetoothapp.presentation.BaseStateBluetoothDataToUiMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    @Singleton
    abstract fun bindMessageToEntityMapper(
        mapper: BaseMessageToEntityMapper
    ) : MessageToEntityMapper

    @Binds
    @Singleton
    abstract fun bindMessageEntityToDataMapper(
        mapper: BaseMessageEntityToDataMapper
    ) : MessageEntityToDataMapper

    @Binds
    @Singleton
    abstract fun bindStateBluetoothDataToUiMapper(
        mapper : BaseStateBluetoothDataToUiMapper
    ) : StateBluetoothDataToUiMapper



}