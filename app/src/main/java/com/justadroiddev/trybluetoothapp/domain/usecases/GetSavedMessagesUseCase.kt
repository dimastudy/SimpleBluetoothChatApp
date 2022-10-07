package com.justadroiddev.trybluetoothapp.domain.usecases

import com.justadroiddev.trybluetoothapp.domain.Repository
import javax.inject.Inject

class GetSavedMessagesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(from: String, to: String) = repository.getMessages(from, to)
}