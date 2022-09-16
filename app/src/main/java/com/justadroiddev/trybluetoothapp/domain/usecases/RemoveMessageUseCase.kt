package com.justadroiddev.trybluetoothapp.domain.usecases

import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.domain.Repository

class RemoveMessageUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(message: Message) = repository.deleteMessage(message)
}