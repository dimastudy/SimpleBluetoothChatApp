package com.justadroiddev.trybluetoothapp.domain.usecases

import javax.inject.Inject

data class BluetoothWorkUseCases @Inject constructor(
    val sendMessageUseCase: SendMessageUseCase,
    val startClientUseCase: StartClientUseCase,
    val startServerUseCase: StartServerUseCase
) {
}