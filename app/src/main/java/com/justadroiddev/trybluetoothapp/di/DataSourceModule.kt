package com.justadroiddev.trybluetoothapp.di

import com.justadroiddev.trybluetoothapp.data.database.CacheDataSource
import com.justadroiddev.trybluetoothapp.data.database.MessagesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {


    @Binds
    @Singleton
    abstract fun bindCacheDataSource(
        dataSource: MessagesDataSource
    ) : CacheDataSource

}