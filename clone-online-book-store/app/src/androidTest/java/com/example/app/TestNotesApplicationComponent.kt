package com.example.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector


interface TestNotesApplicationComponent : AndroidInjector<TestNotesApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestNotesApplicationComponent
    }
}
