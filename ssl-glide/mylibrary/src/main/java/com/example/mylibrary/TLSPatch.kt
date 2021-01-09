package com.example.mylibrary

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import java.security.*
import java.security.cert.X509Certificate
import javax.net.ssl.*


object TLSPatch {
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
    fun allSystemTrustManagers(): Array<TrustManager> {
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        return arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }

    @JvmStatic
    @Throws(GeneralSecurityException::class)
    fun systemDefaultTrustManager(): X509TrustManager {
        val trustManagers =
            allSystemTrustManagers()
        check(!(trustManagers.size != 1 || trustManagers.first() !is X509TrustManager)) {
            "Unexpected default trust managers: ${trustManagers.contentToString()}"
        }
        return trustManagers.first() as X509TrustManager
    }

    /**
     * SSLSocketFactory implementation that enforces TLSv1.2 usage.
     *
     *
     * credit: https://gist.github.com/fkrauthan/ac8624466a4dee4fd02f
     */
    class TLSSocketFactory : SSLSocketFactory() {
        private val internalSSLSocketFactory: SSLSocketFactory
        override fun getDefaultCipherSuites(): Array<String> {
            return internalSSLSocketFactory.defaultCipherSuites
        }

        override fun getSupportedCipherSuites(): Array<String> {
            return internalSSLSocketFactory.supportedCipherSuites
        }

        @Throws(IOException::class)
        override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket {
            return enableTLSOnSocket(
                internalSSLSocketFactory.createSocket(s, host, port, autoClose)
            )
        }

        @Throws(IOException::class, UnknownHostException::class)
        override fun createSocket(host: String, port: Int): Socket {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port))
        }

        @Throws(IOException::class)
        override fun createSocket(
            host: String,
            port: Int,
            localHost: InetAddress,
            localPort: Int
        ): Socket {
            return enableTLSOnSocket(
                internalSSLSocketFactory.createSocket(host, port, localHost, localPort)
            )
        }

        @Throws(IOException::class)
        override fun createSocket(host: InetAddress, port: Int): Socket {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port))
        }

        @Throws(IOException::class)
        override fun createSocket(
            address: InetAddress,
            port: Int,
            localAddress: InetAddress,
            localPort: Int
        ): Socket {
            return enableTLSOnSocket(
                internalSSLSocketFactory.createSocket(address, port, localAddress, localPort)
            )
        }

        private fun enableTLSOnSocket(socket: Socket): Socket {
            if (socket is SSLSocket) {
                socket.enabledProtocols = arrayOf("TLSv1.2")
            }
            return socket
        }

        init {
            val trustAllCerts =
                allSystemTrustManagers()
            val context = SSLContext.getInstance("SSL")
            context.init(null, trustAllCerts, SecureRandom())
            internalSSLSocketFactory = context.socketFactory
        }
    }
}