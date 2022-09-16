package com.justadroiddev.trybluetoothapp.data

import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi

interface StateBluetoothDataToUiMapper {

    fun map(infoTextId: Int, canWrite: Boolean) : StateBluetoothUi

    fun map(bytesMessage: Int?, buffer: ByteArray, color: Int) : StateBluetoothUi

}