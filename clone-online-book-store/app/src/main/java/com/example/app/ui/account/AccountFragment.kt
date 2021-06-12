package com.example.app.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.app.databinding.AccountFragmentBinding
import com.example.app.util.BaseFragment
import com.google.android.material.snackbar.Snackbar

class AccountFragment : BaseFragment() {

    private lateinit var binding: AccountFragmentBinding
    private val viewModel: AccountViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.profile = viewModel.profile

        binding.notificationsSettingText.setOnClickListener(::showNotificationSettings)
        binding.faqSettingText.setOnClickListener(::showFaq)
        binding.policySettingText.setOnClickListener(::showPolicy)
    }

    private fun showNotificationSettings(view: View?) {
        Snackbar.make(binding.root, "Tapped notification setting", Snackbar.LENGTH_SHORT).show()
    }

    private fun showFaq(view: View?) {
        Snackbar.make(binding.root, "Tapped FAQ", Snackbar.LENGTH_SHORT).show()
    }

    private fun showPolicy(view: View?) {
        Snackbar.make(binding.root, "Tapped policy", Snackbar.LENGTH_SHORT).show()
    }
}
