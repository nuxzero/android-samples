package com.example.app

import com.example.app.di.DaggerNotesApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class NotesApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerNotesApplicationComponent.factory().create(applicationContext)
    }
}
