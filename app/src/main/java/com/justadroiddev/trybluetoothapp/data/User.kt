package com.justadroiddev.trybluetoothapp.data

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val device: BluetoothDevice
) : Parcelable{



}