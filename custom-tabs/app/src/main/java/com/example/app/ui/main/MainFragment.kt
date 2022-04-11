package com.example.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app.R

// Custom Tabs URL
private const val testUrl =
    "https://1520-2405-9800-b600-4824-6b-276c-f078-d200.ngrok.io/loyggo1qnihvhmmbhmleoawbks0y1yz4bc9b2/Custom%20Tabs/test-page.html"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var button: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        button = view.findViewById(R.id.button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        button.setOnClickListener {
            launchCustomTabs()
        }
    }

    private fun launchCustomTabs() {
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            .build()

        val customTabsIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(defaultColors)
            .build()

        customTabsIntent.intent.apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(CustomTabsIntent.EXTRA_SHARE_STATE, CustomTabsIntent.SHARE_STATE_OFF)
        }
        customTabsIntent.launchUrl(requireContext(), testUrl.toUri())
    }
}
