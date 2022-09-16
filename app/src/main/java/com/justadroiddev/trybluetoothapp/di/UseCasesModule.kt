package com.justadroiddev.trybluetoothapp.di

import com.justadroiddev.trybluetoothapp.domain.Repository
import com.justadroiddev.trybluetoothapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideStartServerUseCase(
        repository: Repository
    ): StartServerUseCase = StartServerUseCase(repository)

    @Provides
    @Singleton
    fun provideStartClientUseCase(
        repository: Repository
    ) : StartClientUseCase = StartClientUseCase(repository)

    @Provides
    @Singleton
    fun provideSendMessageUseCase(
        repository: Repository
    ) : SendMessageUseCase = SendMessageUseCase(repository)

    @Provides
    @Singleton
    fun provideBluetoothWorkUseCases(
        sendMessageUseCase: SendMessageUseCase,
        startClientUseCase: StartClientUseCase,
        startServerUseCase: StartServerUseCase
    ) : BluetoothWorkUseCases = BluetoothWorkUseCases(sendMessageUseCase, startClientUseCase, startServerUseCase)

    @Provides
    @Singleton
    fun provideSaveMessageUseCase(
        repository: Repository
    ) : SaveMessageUseCase = SaveMessageUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveMessageUseCase(
        repository: Repository
    ) : RemoveMessageUseCase = RemoveMessageUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMessagesUseCase(
        repository: Repository
    ) : GetSavedMessagesUseCase = GetSavedMessagesUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateMessageUseCase(
        repository: Repository
    ) : UpdateMessageUseCase = UpdateMessageUseCase(repository)

    @Provides
    @Singleton
    fun provideMessagesWorkUseCase(
        saveMessageUseCase: SaveMessageUseCase,
        removeMessageUseCase: RemoveMessageUseCase,
        updateMessageUseCase: UpdateMessageUseCase,
        getSavedMessagesUseCase: GetSavedMessagesUseCase
    ) : MessagesWorkUseCase = MessagesWorkUseCase(saveMessageUseCase, removeMessageUseCase, updateMessageUseCase, getSavedMessagesUseCase)


}