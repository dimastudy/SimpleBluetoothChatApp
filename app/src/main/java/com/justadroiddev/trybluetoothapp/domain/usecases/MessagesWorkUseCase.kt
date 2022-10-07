package com.justadroiddev.trybluetoothapp.domain.usecases

import javax.inject.Inject

data class MessagesWorkUseCase @Inject constructor(
    val saveMessageUseCase: SaveMessageUseCase,
    val removeMessageUseCase: RemoveMessageUseCase,
    val updateMessageUseCase: UpdateMessageUseCase,
    val getSavedMessagesUseCase: GetSavedMessagesUseCase
)