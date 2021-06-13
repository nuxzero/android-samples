package com.example.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener


class NotesApplicationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        DexOpener.install(this)
        // TODO: Setup Dagger for testing in the TestNotesApplication
        // return super.newApplication(cl, TestNotesApplication::class.java.name, context)
        return super.newApplication(cl, className, context)
    }
}
