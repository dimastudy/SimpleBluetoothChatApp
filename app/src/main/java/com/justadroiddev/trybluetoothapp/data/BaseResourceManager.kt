package com.justadroiddev.trybluetoothapp.data

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


class BaseResourceManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceManager {
    override fun provideString(@StringRes id: Int): String =
        context.resources.getString(id)

    override fun provideColor(id: Int): Int =
        context.resources.getColor(id, null)


}