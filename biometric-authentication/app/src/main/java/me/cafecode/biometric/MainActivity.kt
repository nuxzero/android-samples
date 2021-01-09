package me.cafecode.biometric

import android.annotation.TargetApi
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

@TargetApi(Build.VERSION_CODES.P)
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{showBiometricAuth()}
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    fun showBiometricAuth() {
        // TODO: 2
        val executor = Executor {
            Toast.makeText(this, "Do something", Toast.LENGTH_SHORT).show()
        }

        val clickListener= DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        val promt = BiometricPrompt.Builder(this)
                .setTitle("Verify user")
                .setSubtitle("Required Android P")
                .setDescription("Use biometric to verify user")
                .setNegativeButton("Cancel", executor, clickListener)
                .build()

        val authenticationCallback = object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)

                Toast.makeText(this@MainActivity, "Do something onAuthenticationError()", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)

                Toast.makeText(this@MainActivity, "Do something onAuthenticationSucceeded()", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpCode, helpString)

                Toast.makeText(this@MainActivity, "Do something onAuthenticationHelp()", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                Toast.makeText(this@MainActivity, "Do something onAuthenticationFailed()", Toast.LENGTH_SHORT).show()
            }
        }
        promt.authenticate(CancellationSignal(), this.mainExecutor, authenticationCallback)
    }
}
