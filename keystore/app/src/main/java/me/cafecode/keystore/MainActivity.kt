package me.cafecode.keystore

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var keyStoreManager: KeyStoreManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyStoreManager = PostMarshmallowKeyStoreManager()
        } else {
            val context = applicationContext
            keyStoreManager = PreMarshmallowKeyStoreManager(context)
        }

        encrypt_button.setOnClickListener {

            val encryptedData = keyStoreManager.encryptData(data_input.text.toString())
            encrypted_input.setText(encryptedData)

            val decryptedData = keyStoreManager.decryptData(encryptedData)
            decrypted_text.text = decryptedData
        }

        decrypt_button.setOnClickListener {
            val decryptedData = keyStoreManager.decryptData(encrypted_input.text.toString())
            decrypted_text.text = decryptedData
        }
    }
}
