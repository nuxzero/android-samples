package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.app.databinding.ActivityMainBinding
import org.jetbrains.annotations.TestOnly

class MainActivity : AppCompatActivity() {
    private var viewModelFactory: ViewModelProvider.Factory? = null
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory ?: MainViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.viewModel = viewModel

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        /**
         * Why should we not call this here?
         * To avoid calling this method when the activity is recreated (e.g. on configuration change).
         * Instead, we should call it in the ViewModel's init block.
         */
        // viewModel.loadUser()
    }

    @TestOnly
    fun setViewModelFactory(factory: ViewModelProvider.Factory) {
        viewModelFactory = factory
    }
}
