package me.cafecode.keystore

import android.annotation.TargetApi
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.Key
import java.security.KeyStore
import java.security.UnrecoverableKeyException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

@TargetApi(23)
class PostMarshmallowKeyStoreManager : KeyStoreManager() {

    // IV (Initialization Vector) has fix size 12 bytes for GCM modes
    private val IV = "co.omise.ivx"


    private val parameterSpec: GCMParameterSpec by lazy { GCMParameterSpec(128, IV.toByteArray()) }

    override fun getAliasName(): String {
        return "me.cafecode.keystore.alias"
    }

    override fun getAlgorithm(): String {
        return "AES/GCM/NoPadding"
    }

    override fun generateKey() {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore?.load(null)
        if (keyStore != null && keyStore?.containsAlias(getAliasName()) == true) {
            return
        }
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val keySpec = KeyGenParameterSpec.Builder(getAliasName(), KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(false)
                .build()
        keyGenerator.init(keySpec)
        keyGenerator.generateKey()
    }

    override fun encryptData(data: String): String {
        val key: Key? = try {
            keyStore?.getKey(getAliasName(), null)
        } catch (e: UnrecoverableKeyException) {
            throw e
        }
        val cipher: Cipher = Cipher.getInstance(getAlgorithm())
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec)
        val bytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    override fun decryptData(data: String): String {
        val key: Key? = try {
            keyStore?.getKey(getAliasName(), null)
        } catch (e: UnrecoverableKeyException) {
            throw e
        }
        val cipher: Cipher = Cipher.getInstance(getAlgorithm())
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec)
        val encryptedBytes = Base64.decode(data, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }
}