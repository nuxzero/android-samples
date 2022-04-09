package com.example.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.run {
            button.setOnClickListener {
                CustomDialogFragment.newInstance(
                    dialogType = DialogType.DANGER,
                    title = "Dialog title",
                    message = "Dialog message.",
                    negativeButton = "Cancel",
                    positiveButton = "OK",
                    onPositiveClickListener = {
                        Log.i("MainFragment", "clicked positive")
                    }
                ).show(childFragmentManager, null)
            }
        }
    }
}
