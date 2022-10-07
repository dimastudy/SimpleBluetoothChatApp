package com.justadroiddev.trybluetoothapp.domain.usecases

import com.justadroiddev.trybluetoothapp.data.BluetoothService
import com.justadroiddev.trybluetoothapp.data.StateBluetoothData
import com.justadroiddev.trybluetoothapp.data.User
import com.justadroiddev.trybluetoothapp.domain.Repository
import com.justadroiddev.trybluetoothapp.presentation.ChatCallback
import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi
import javax.inject.Inject


class StartClientUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(colorClient: Int, user: User?, chatCallback: ChatCallback) =
        repository.doClientWork(colorClient, user, chatCallback)
}