package com.justadroiddev.trybluetoothapp.presentation.ui.start

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.justadroiddev.trybluetoothapp.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import java.util.logging.Handler
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class StartViewModel @Inject constructor() : ViewModel() {

    private val listDevices = MutableLiveData<List<User>>()

    fun observeListDevices(owner: LifecycleOwner, observer: Observer<List<User>>){
        listDevices.observe(owner, observer)
    }

    fun updateNearDevices(bluetoothAdapter: BluetoothAdapter){
        val bondedDevices = bluetoothAdapter.bondedDevices
        val newListDevices = ArrayList<User>()

        if (bondedDevices.size > 0){
            for (device: BluetoothDevice in bondedDevices){
                newListDevices.add(User(device.name, device))
            }
        }
        listDevices.value = newListDevices
    }

}


