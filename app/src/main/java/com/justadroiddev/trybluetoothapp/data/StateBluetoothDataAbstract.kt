package com.justadroiddev.trybluetoothapp.data

import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi

interface StateBluetoothDataAbstract {

    fun map(mapper: StateBluetoothDataToUiMapper) : StateBluetoothUi

}