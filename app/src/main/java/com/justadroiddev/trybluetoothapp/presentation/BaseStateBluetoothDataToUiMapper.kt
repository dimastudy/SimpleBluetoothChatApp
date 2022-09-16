package com.justadroiddev.trybluetoothapp.presentation

import android.util.Log
import com.justadroiddev.trybluetoothapp.data.Message
import com.justadroiddev.trybluetoothapp.data.ResourceManager
import com.justadroiddev.trybluetoothapp.data.StateBluetoothDataToUiMapper

class BaseStateBluetoothDataToUiMapper(
    private val resourceManager: ResourceManager
) : StateBluetoothDataToUiMapper {
    override fun map(infoTextId: Int, canWrite: Boolean): StateBluetoothUi =
        StateBluetoothUi.InfoMessage(resourceManager.provideString(infoTextId), canWrite)


    override fun map(bytesMessage: Int?, buffer: ByteArray, color: Int): StateBluetoothUi {
        if (bytesMessage != null) {
            val stringMessage = String(buffer, 0, bytesMessage)
            val nameFromUser = stringMessage.substringAfter("@fU@").substringBefore("@tU@")
            val nameToUser = stringMessage.substringAfter("@tU@").substringBefore("@dm@")
            val idDelete = stringMessage.substringAfter("@dm@")
            val myMessage = stringMessage.substringBefore("@fU")
            Log.e("Mapper", "idDelete -> $idDelete")
            return StateBluetoothUi.MessageReceivedUi(Message(idDelete.toInt() ,myMessage, nameFromUser, nameToUser, color))

        }
        return StateBluetoothUi.MessageReceivedUi(Message(Message.NEW_ADDED_MESSAGE,"Error", "Error", "Error",color))
    }


}