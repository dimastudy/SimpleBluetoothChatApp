package com.justadroiddev.trybluetoothapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<VB: ViewBinding>(
    private val bindingInflater : (inflater: LayoutInflater) -> VB
) : Fragment(){

    private var _binding : VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}