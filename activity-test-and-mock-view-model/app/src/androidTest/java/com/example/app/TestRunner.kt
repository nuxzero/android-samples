package com.example.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener


class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        // Install DexOpener to open all classes.
        DexOpener.install(this)
        return super.newApplication(cl, className, context)
    }
}
