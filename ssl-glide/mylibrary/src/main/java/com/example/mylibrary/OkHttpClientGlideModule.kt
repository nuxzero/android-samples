package com.example.mylibrary

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


@GlideModule
open class OkHttpClientGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpUrlLoader = OkHttpUrlLoader.Factory(Client.okHttpClient)
        registry.replace(GlideUrl::class.java, InputStream::class.java, okHttpUrlLoader)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}