package me.cafecode.keystore

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal


class PreMarshmallowKeyStoreManager(val context: Context) : KeyStoreManager() {

    override fun getAliasName(): String {
        return "me.cafecode.keystore.alias"
    }

    override fun getAlgorithm(): String {
        return "RSA/ECB/PKCS1Padding"
    }

    override fun generateKey() {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore?.load(null)
        if (keyStore != null && keyStore?.containsAlias(getAliasName()) == true) {
            return
        }

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 20)
        val spec = KeyPairGeneratorSpec.Builder(context)
                .setAlias(getAliasName())
                .setSubject(X500Principal("CN=${getAliasName()}"))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()
        val generator = KeyPairGenerator.getInstance(getAlgorithm().split("/").first(), ANDROID_KEY_STORE)
        generator.initialize(spec)

        generator.generateKeyPair()
    }

    override fun encryptData(data: String): String {
        try {
            val privateKeyEntry = keyStore!!.getEntry(getAliasName(), null) as KeyStore.PrivateKeyEntry
            val publicKey = privateKeyEntry.certificate.publicKey as RSAPublicKey


            val cipher = Cipher.getInstance(getAlgorithm())
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)

            val encryptedBytes = cipher.doFinal(data.toByteArray())
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun decryptData(data: String): String {
        try {
            val privateKeyEntry = keyStore!!.getEntry(getAliasName(), null) as KeyStore.PrivateKeyEntry
            val privateKey = privateKeyEntry.privateKey as RSAPrivateKey

            val cipher = Cipher.getInstance(getAlgorithm())
            cipher.init(Cipher.DECRYPT_MODE, privateKey)

            val encryptedBytes = Base64.decode(data, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes)
        } catch (e: Exception) {
            throw e
        }

    }
}