package com.justadroiddev.trybluetoothapp.domain.usecases

import com.justadroiddev.trybluetoothapp.domain.Repository

class GetSavedMessagesUseCase(
    private val repository: Repository
) {
    operator fun invoke(from: String, to: String) = repository.getMessages(from, to)
}