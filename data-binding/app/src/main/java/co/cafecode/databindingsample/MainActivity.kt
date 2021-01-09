package co.cafecode.databindingsample

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.cafecode.databindingsample.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding
    var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        }
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }
}
