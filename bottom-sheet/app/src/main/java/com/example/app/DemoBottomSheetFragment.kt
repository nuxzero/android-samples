package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.databinding.DemoBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DemoBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DemoBottomSheetFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DemoBottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}
