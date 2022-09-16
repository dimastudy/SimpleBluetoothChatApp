package com.justadroiddev.trybluetoothapp.data

import android.bluetooth.*
import android.content.Context
import android.util.Log
import com.justadroiddev.trybluetoothapp.presentation.ChatCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BaseBluetoothService(
    private val context: Context,
    private val stateBluetoothDataToUiMapper: StateBluetoothDataToUiMapper
) : BluetoothService {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var sendService: SendService
    private lateinit var connectedUser: String
    private var isDataSourceUpdated = false

    init {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    override suspend fun doServerWork(
        colorClient: Int,
        chatCallback: ChatCallback
    ) {
        var serverSocket: BluetoothServerSocket? = null
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(
                APP_NAME,
                PHONE_UUID
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        withContext(Dispatchers.IO) {
            var socket: BluetoothSocket? = null
            while (socket == null) {
                try {
                    chatCallback.updateStatus(
                        StateBluetoothData.Connecting.map(
                            stateBluetoothDataToUiMapper
                        )
                    )
                    socket = serverSocket?.accept()
                } catch (e: IOException) {
                    e.printStackTrace()
                    chatCallback.updateStatus(
                        StateBluetoothData.ConnectionFailed.map(
                            stateBluetoothDataToUiMapper
                        )
                    )
                }
                if (socket != null) {
                    connectedUser = socket.remoteDevice.name
                    if (!isDataSourceUpdated) {
                        Log.e("BluetoothService", "Server device")
                        chatCallback.updateChatMessages(bluetoothAdapter.name, connectedUser)
                        isDataSourceUpdated = true
                    }
                    chatCallback.updateStatus(
                        StateBluetoothData.Connected.map(
                            stateBluetoothDataToUiMapper
                        )
                    )
                    sendService = SendService(socket)
                    sendService.read(colorClient, chatCallback)
                    break
                }
            }
        }
    }

    override suspend fun doClientWork(
        colorClient: Int,
        user: User?,
        chatCallback: ChatCallback
    ) {
        var clientDevice: BluetoothDevice? = null
        var socket: BluetoothSocket? = null
        clientDevice = user?.device
        try {
            socket = clientDevice?.createRfcommSocketToServiceRecord(PHONE_UUID)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        withContext(Dispatchers.IO) {
            try {
                socket?.connect()
                chatCallback.updateStatus(
                    StateBluetoothData.Connected.map(
                        stateBluetoothDataToUiMapper
                    )
                )
                if (socket != null) {
                    if (!isDataSourceUpdated) {
                        Log.e("BluetoothService", "Client device")
                        chatCallback.updateChatMessages(bluetoothAdapter.name, user?.name!!)
                        isDataSourceUpdated = true
                    }
                    sendService = SendService(socket)
                    sendService.read(colorClient, chatCallback)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                chatCallback.updateStatus(
                    StateBluetoothData.ConnectionFailed.map(
                        stateBluetoothDataToUiMapper
                    )
                )
            }
        }
    }

    override suspend fun sendMessage(
        user: User?,
        message: String,
        color: Int,
        updateListMessages: (message: Message) -> Unit,
        idDelete: Int
    ) {
        if (::sendService.isInitialized) {
            val userName = bluetoothAdapter.name
            val toUser = user?.name ?: connectedUser
            val sendingMessage = "$message@fU@$userName@tU@$toUser@dm@$idDelete"
            sendService.write(sendingMessage.toByteArray())
            updateListMessages(Message(0, message, userName, toUser, color))
        }
    }


    inner class SendService(
        private val socket: BluetoothSocket
    ) {

        var bluetoothSocket: BluetoothSocket? = null
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        init {
            bluetoothSocket = socket
            var tempInputStream: InputStream? = null
            var tempOutputStream: OutputStream? = null
            try {
                tempInputStream = bluetoothSocket?.inputStream
                tempOutputStream = bluetoothSocket?.outputStream
            } catch (e: IOException) {
                e.printStackTrace()
            }
            inputStream = tempInputStream
            outputStream = tempOutputStream
        }

        suspend fun read(color: Int, chatCallback: ChatCallback) {
            withContext(Dispatchers.IO) {
                var buffer = ByteArray(1024)
                var bytes: Int? = 0

                while (true) {
                    try {
                        bytes = inputStream?.read(buffer)
                        chatCallback.updateStatus(
                            StateBluetoothData.MessageReceived(bytes, buffer, color)
                                .map(stateBluetoothDataToUiMapper)
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        fun write(bytes: ByteArray) {
            try {
                outputStream?.write(bytes)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val APP_NAME = "TryBluetoothChat"
        private val PHONE_UUID = UUID.fromString("9be22a64-2ce6-11ed-a261-0242ac120002")
    }

}