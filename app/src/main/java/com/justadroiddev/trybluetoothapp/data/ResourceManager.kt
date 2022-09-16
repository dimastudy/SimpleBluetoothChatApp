package com.justadroiddev.trybluetoothapp.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceManager {

    fun provideString(@StringRes id: Int) : String

    fun provideColor(@ColorRes id: Int) : Int

}