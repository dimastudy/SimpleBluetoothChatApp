package com.justadroiddev.trybluetoothapp.domain.usecases

data class BluetoothWorkUseCases(
    val sendMessageUseCase: SendMessageUseCase,
    val startClientUseCase: StartClientUseCase,
    val startServerUseCase: StartServerUseCase
) {
}