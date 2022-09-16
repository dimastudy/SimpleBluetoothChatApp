package com.justadroiddev.trybluetoothapp.presentation.ui.chat

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.justadroiddev.trybluetoothapp.R
import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.data.ResourceManager
import com.justadroiddev.trybluetoothapp.data.User
import com.justadroiddev.trybluetoothapp.domain.usecases.BluetoothWorkUseCases
import com.justadroiddev.trybluetoothapp.presentation.MessagesStatus
import com.justadroiddev.trybluetoothapp.data.StateBluetoothData
import com.justadroiddev.trybluetoothapp.domain.usecases.MessagesWorkUseCase
import com.justadroiddev.trybluetoothapp.presentation.ChatCallback
import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer


class ChatViewModel @AssistedInject constructor(
    @Assisted private val user: User?,
    @Assisted private val clientColor: Int,
    private val bluetoothWorkUseCases: BluetoothWorkUseCases,
    private val messagesWorkUseCase: MessagesWorkUseCase,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val state = MutableLiveData<StateBluetoothUi>()
    private val listMessages = MutableLiveData<MessagesStatus>(MessagesStatus.Empty)
    private val chatCallback = object : ChatCallback {
        override fun updateStatus(status: StateBluetoothUi) {
            changeStatus(status)
        }

        override fun updateChatMessages(from: String, to: String) {
            updateMessages(from, to)
        }

    }


    init {
        if (user != null) {
            clientPart()
        } else {
            serverPart()
        }
    }

    fun updateMessages(from: String, to: String) = viewModelScope.launch {
        val fetchMessages = async(Dispatchers.IO) {
            messagesWorkUseCase.getSavedMessagesUseCase(from, to)
        }
        val messages = fetchMessages.await()
        if (!messages.isNullOrEmpty()) {
            listMessages.value = MessagesStatus.MessageExists(messages)
        } else {
            listMessages.value = MessagesStatus.MessageExists(emptyList<Message>())
            listMessages.value = MessagesStatus.Empty
        }
    }

    fun removeMessage(message: Message, userName: String) = viewModelScope.launch {
        val deletingProcess = async(Dispatchers.IO) {
            messagesWorkUseCase.removeMessageUseCase(message)
        }
        deletingProcess.await()
        updateMessages(message.fromUser, message.toUser)
        if (message.fromUser == userName) {
            withContext(Dispatchers.IO) {
                bluetoothWorkUseCases.sendMessageUseCase(
                    user,
                    message.message,
                    message.userColor,
                    {},
                    message.id
                )
            }
        }
    }

    private fun updateMessage(message: Message) = viewModelScope.launch {
        val updatingProcess = async(Dispatchers.IO) {
            messagesWorkUseCase.updateMessageUseCase(
                message.changeTextMessage("Message was deleted").changeUserColor(
                    resourceManager.provideColor(
                        R.color.message_deleted
                    )
                )
            )
        }
        updatingProcess.await()
        updateMessages(message.fromUser, message.toUser)
    }

    fun observeListMessages(owner: LifecycleOwner, observer: Observer<MessagesStatus>) {
        listMessages.observe(owner, observer)
    }

    fun observeStatus(owner: LifecycleOwner, observer: Observer<StateBluetoothUi>) {
        state.observe(owner, observer)
    }

    fun changeStatus(newStatus: StateBluetoothUi) = viewModelScope.launch {
        state.postValue(newStatus)
    }

    fun addMessage(message: Message) = viewModelScope.launch {
        Log.e(
            "ChatViewModel",
            "------------------------------\nfrom -> ${message.fromUser}\nto -> ${message.toUser}"
        )
        val savingProcess = async(Dispatchers.IO) {
            messagesWorkUseCase.saveMessageUseCase(message)
        }
        savingProcess.await()
        updateMessages(message.fromUser, message.toUser)
    }

    fun checkMessage(message: Message) {
        Log.e("ChatViewModel", "------------------\nId: ${message.id}")
        if (message.id == Message.NEW_ADDED_MESSAGE) {
            addMessage(message)
        } else {
            updateMessage(message)
        }
    }

    private fun serverPart() = viewModelScope.launch(Dispatchers.IO) {
        bluetoothWorkUseCases.startServerUseCase(clientColor, chatCallback)
    }

    fun provideAlias(action: (aliasText: String?) -> Unit) {
        action(user?.device?.alias)
    }

    private fun clientPart() = viewModelScope.launch(Dispatchers.IO) {
        bluetoothWorkUseCases.startClientUseCase(clientColor, user, chatCallback)
    }

    fun sendMessage(message: String, color: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothWorkUseCases.sendMessageUseCase(user, message, color, { message ->
                addMessage(message)
            }, Message.NEW_ADDED_MESSAGE)
        }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            user: User?,
            color: Int,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                    return assistedFactory.create(user, color) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            user: User?,
            color: Int,
        ): ChatViewModel
    }

}



