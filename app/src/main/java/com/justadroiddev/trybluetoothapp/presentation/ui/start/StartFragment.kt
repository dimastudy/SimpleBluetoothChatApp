package com.justadroiddev.trybluetoothapp.presentation.ui.start

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.justadroiddev.trybluetoothapp.databinding.FragmentStartBinding
import com.justadroiddev.trybluetoothapp.presentation.adapters.DevicesListAdapter
import com.justadroiddev.trybluetoothapp.presentation.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    val viewModel : StartViewModel by viewModels()

    companion object {
        private const val REQUEST_CODE_BLUETOOTH_ENABLED_OK = 1
        private const val PERMISSION_REQUEST_CODE = 100
    }

    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter

    val bluetoothActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK){
                val data = activityResult.data
                Snackbar.make(requireView(), "Enabled", Snackbar.LENGTH_SHORT).show()
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bluetoothManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        val bf = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (bluetoothAdapter != null){
            if (!bluetoothAdapter.isEnabled) {
                bluetoothActivityResultLauncher.launch(bf)
            }
        }
        checkBluetoothPermission(){}
        val adapter = DevicesListAdapter(DevicesListAdapter.DeviceClickListener { user ->
            this.findNavController().navigate(StartFragmentDirections.actionStartFragmentToChatFragment3(user))
            Snackbar.make(requireView(), user.name, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.observeListDevices(viewLifecycleOwner) { listDevices ->
            adapter.submitList(listDevices)
        }



        binding.apply {
            checkBluetoothBtn.setOnClickListener {
                if (bluetoothAdapter != null){
                    if (bluetoothAdapter.isEnabled){
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R){
                            checkBluetoothPermission(){
                                viewModel.updateNearDevices(bluetoothAdapter)
                            }
                        } else {
                            viewModel.updateNearDevices(bluetoothAdapter)
                        }
                    } else {
                        bluetoothActivityResultLauncher.launch(bf)
                    }
                }
            }
            btnListenDevices.setOnClickListener {
                if (bluetoothAdapter != null){
                    if (bluetoothAdapter.isEnabled){
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R){
                            checkBluetoothPermission {
                                this@StartFragment.findNavController().navigate(StartFragmentDirections.actionStartFragmentToChatFragment3(null))
                            }
                        } else {
                            this@StartFragment.findNavController().navigate(StartFragmentDirections.actionStartFragmentToChatFragment3(null))
                        }
                    } else {
                        bluetoothActivityResultLauncher.launch(bf)
                    }
                }
            }
            rvListDevices.setHasFixedSize(true)
            rvListDevices.adapter = adapter
        }

    }

    fun checkBluetoothPermission(action : () -> Unit) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R){
            if (requireActivity().checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.BLUETOOTH_CONNECT), PERMISSION_REQUEST_CODE)
            } else {
                action()
            }
        }
    }

}