package com.justadroiddev.trybluetoothapp.domain.usecases

import com.justadroiddev.trybluetoothapp.data.BluetoothService
import com.justadroiddev.trybluetoothapp.data.StateBluetoothData
import com.justadroiddev.trybluetoothapp.domain.Repository
import com.justadroiddev.trybluetoothapp.presentation.ChatCallback
import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi
import javax.inject.Inject


class StartServerUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(colorClient: Int, chatCallback: ChatCallback) =
        repository.doServerWork(colorClient, chatCallback)
}