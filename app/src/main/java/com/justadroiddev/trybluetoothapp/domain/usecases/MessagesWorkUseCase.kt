package com.justadroiddev.trybluetoothapp.domain.usecases

data class MessagesWorkUseCase(
    val saveMessageUseCase: SaveMessageUseCase,
    val removeMessageUseCase: RemoveMessageUseCase,
    val updateMessageUseCase: UpdateMessageUseCase,
    val getSavedMessagesUseCase: GetSavedMessagesUseCase
)