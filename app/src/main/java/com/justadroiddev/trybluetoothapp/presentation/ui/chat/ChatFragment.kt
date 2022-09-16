package com.justadroiddev.trybluetoothapp.presentation.ui.chat

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.justadroiddev.trybluetoothapp.R
import com.justadroiddev.trybluetoothapp.databinding.FragmentChatBinding
import com.justadroiddev.trybluetoothapp.data.BaseBluetoothService
import com.justadroiddev.trybluetoothapp.data.BaseResourceManager
import com.justadroiddev.trybluetoothapp.domain.usecases.BluetoothWorkUseCases
import com.justadroiddev.trybluetoothapp.domain.usecases.SendMessageUseCase
import com.justadroiddev.trybluetoothapp.domain.usecases.StartClientUseCase
import com.justadroiddev.trybluetoothapp.domain.usecases.StartServerUseCase
import com.justadroiddev.trybluetoothapp.presentation.MessagesStatus
import com.justadroiddev.trybluetoothapp.data.StateBluetoothData
import com.justadroiddev.trybluetoothapp.presentation.BaseStateBluetoothDataToUiMapper
import com.justadroiddev.trybluetoothapp.presentation.StateBluetoothUi
import com.justadroiddev.trybluetoothapp.presentation.adapters.MessagesListAdapter
import com.justadroiddev.trybluetoothapp.presentation.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter

    @Inject
    lateinit var factory: ChatViewModel.AssistedFactory

    private val viewModel : ChatViewModel by viewModels<ChatViewModel> {
        val user = ChatFragmentArgs.fromBundle(requireArguments()).userDevice
        ChatViewModel.provideFactory(factory, user, resources.getColor(R.color.user_message_color, null))
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bluetoothManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        viewModel.observeStatus(viewLifecycleOwner) { status ->
            when(status){
                is StateBluetoothUi.InfoMessage -> {
                    binding.statusText.text = status.message
                    binding.btnSendMessage.isEnabled = status.canWrite
                }
                is StateBluetoothUi.MessageReceivedUi -> {
                    viewModel.checkMessage(status.message)
                }
                else -> {
                    binding.statusText.text = "Some Shit"
                }
            }
        }

        val adapter = MessagesListAdapter(bluetoothAdapter.name, MessagesListAdapter.ClickListener{ message, userName ->
            viewModel.removeMessage(message, userName)
        })

        viewModel.observeListMessages(viewLifecycleOwner) { messageStatus ->
            when(messageStatus) {
                is MessagesStatus.Empty -> {
                    binding.textNoMessages.visibility = View.VISIBLE
                }
                is MessagesStatus.MessageExists -> {
                    binding.textNoMessages.visibility = View.GONE
                    adapter.submitList(messageStatus.data)
                    adapter.notifyDataSetChanged()
                    binding.rvListMessages.smoothScrollToPosition(messageStatus.data.size)
                }
            }
        }

        binding.apply {
            rvListMessages.setHasFixedSize(true)
            rvListMessages.adapter = adapter
            btnSendMessage.setOnClickListener {
                val message = etMessageSend.text.toString()
                viewModel.sendMessage(message, resources.getColor(R.color.my_message_color, null))
                etMessageSend.text.clear()
//                hideKeyBoard()
            }
            statusText.setOnClickListener {
                viewModel.provideAlias { aliasText ->
                    Snackbar.make(requireView(), "$aliasText", Snackbar.LENGTH_LONG).show()
                }
            }
        }


    }

    fun hideKeyBoard(){
        val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}