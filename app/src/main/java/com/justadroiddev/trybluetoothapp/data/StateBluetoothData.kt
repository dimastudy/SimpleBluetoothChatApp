package com.justadroiddev.trybluetoothapp.data

import com.justadroiddev.trybluetoothapp.R
import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi

sealed class StateBluetoothData : StateBluetoothDataAbstract {

    object NonActive : StateBluetoothData() {
        override fun map(mapper: StateBluetoothDataToUiMapper): StateBluetoothUi =
            mapper.map(R.string.non_active_info_message, false)
    }

    object Listening : StateBluetoothData() {
        override fun map(mapper: StateBluetoothDataToUiMapper): StateBluetoothUi =
            mapper.map(R.string.connecting_info_message, false)
    }

    object Connecting : StateBluetoothData() {
        override fun map(mapper: StateBluetoothDataToUiMapper): StateBluetoothUi =
            mapper.map(R.string.connecting_info_message, false)
    }

    object Connected : StateBluetoothData() {
        override fun map(mapper: StateBluetoothDataToUiMapper): StateBluetoothUi =
            mapper.map(R.string.connected_info_message, true)
    }

    object ConnectionFailed : StateBluetoothData() {
        override fun map(mapper: StateBluetoothDataToUiMapper): StateBluetoothUi =
            mapper.map(R.string.failed_info_message, false)
    }

    class MessageReceived(private val bytesMessage: Int?, private val buffer: ByteArray, private val color: Int) :
        StateBluetoothData() {
        override fun map(mapper: StateBluetoothDataToUiMapper): StateBluetoothUi =
            mapper.map(bytesMessage, buffer, color)
    }

}
