package com.example.mylibrary

import com.example.mylibrary.TLSPatch
import com.example.mylibrary.TLSPatch.systemDefaultTrustManager
import okhttp3.*
import java.security.GeneralSecurityException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory

object Client {

    val okHttpClient: OkHttpClient
        get() {
            val requestTimeout = 10_000L
            val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                )
                .build()

            val httpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(requestTimeout, TimeUnit.SECONDS)
                .writeTimeout(requestTimeout, TimeUnit.SECONDS)
                .readTimeout(requestTimeout, TimeUnit.SECONDS)
                .connectionSpecs(Collections.singletonList(spec))

            try {
                val sslSocketFactory: SSLSocketFactory = TLSPatch.TLSSocketFactory()
                val x509TrustManager = systemDefaultTrustManager()
                httpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager)
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
            }
            return httpClientBuilder.build()
        }
}
