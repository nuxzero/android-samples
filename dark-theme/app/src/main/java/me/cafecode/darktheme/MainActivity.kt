package me.cafecode.darktheme

import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isNightMode() || isAppCompatNightMode()) {
            message.text = "Welcome to night mode"
            switch_theme_button.text = "Switch to day mode"
        } else {
            message.text = "Welcome to day mode"
            switch_theme_button.text = "Switch to night mode"
        }

        switch_theme_button.setOnClickListener {
            val button = it as Button
            if (isNightMode() || isAppCompatNightMode()) {
                turnNightModeOn(false)
                button.text = "Switching to day mode..."
            } else {
                turnNightModeOn()
                button.text = "Switching to night mode..."
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentUiMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val message = when (currentUiMode) {
            Configuration.UI_MODE_NIGHT_NO -> "Changed to Light mode"
            Configuration.UI_MODE_NIGHT_YES -> "Changed to Dark mode"
            else -> "Unknown"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isAppCompatNightMode(): Boolean {
        val uiMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        Log.d("isAppCompatNightMode", "$uiMode")
        return uiMode
    }

    /**
     * Check whether the system configuration is the dark theme.
     * However, #resources.configuration.uiMode can be changed after #AppCompatDelegate.setDefaultNightMode().
     *
     * @return <code>true</code> if dark theme is enable and <code>false</code> if dark theme is disable.
     */
    private fun isNightMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val uiMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES
        Log.d("isNightMode", "$uiMode")
        return uiMode
    }


    private fun turnNightModeOn(turnOn: Boolean = true) {
        if (turnOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        delegate.applyDayNight() //
        recreate()
    }
}
