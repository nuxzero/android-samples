package me.cafecode.keystore

import java.security.KeyStore


abstract class KeyStoreManager {

    protected val ANDROID_KEY_STORE = "AndroidKeyStore"

    var keyStore: KeyStore? = null

    protected abstract fun getAliasName(): String

    protected abstract fun getAlgorithm(): String

    protected abstract fun generateKey()

    fun deleteKey() {
        keyStore?.deleteEntry(getAliasName())
    }

    abstract fun encryptData(data: String): String

    abstract fun decryptData(data: String): String

    init {
        this.generateKey()
    }

}
