package com.justadroiddev.trybluetoothapp.di

import android.content.Context
import com.justadroiddev.trybluetoothapp.data.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindResourceManager(
        manager: BaseResourceManager
    ) : ResourceManager

    @Binds
    @Singleton
    abstract fun bindBluetoothService(
       service: BaseBluetoothService
    ) : BluetoothService

}