package com.justadroiddev.trybluetoothapp.domain.usecases

import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.data.BluetoothService
import com.justadroiddev.trybluetoothapp.data.User
import com.justadroiddev.trybluetoothapp.domain.Repository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(user: User?, message: String, color: Int, updateListMessages: (message: Message) -> Unit, idDelete: Int) =
        repository.sendMessage(user, message, color, updateListMessages, idDelete)
}