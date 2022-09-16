package com.justadroiddev.trybluetoothapp.data

import android.content.Context
import androidx.annotation.StringRes

class BaseResourceManager(
    private val context: Context
) : ResourceManager {
    override fun provideString(@StringRes id: Int): String =
        context.resources.getString(id)

    override fun provideColor(id: Int): Int =
        context.resources.getColor(id, null)


}