package me.cafecode.android.newspaper

import android.app.Application
import me.cafecode.android.newspaper.data.DaggerNewsRepositoryComponent
import me.cafecode.android.newspaper.data.NewsRepositoryComponent
import me.cafecode.android.newspaper.data.NewsRepositoryModule
import me.cafecode.android.newspaper.injection.AppModule

class NewspaperApplication : Application() {

    lateinit var newsRepositoryComponent: NewsRepositoryComponent

    override fun onCreate() {
        super.onCreate()

        //TODO: Inject here
        newsRepositoryComponent = DaggerNewsRepositoryComponent.builder()
                .newsRepositoryModule(NewsRepositoryModule(this.applicationContext))
                .appModule(AppModule())
                .build()
    }

}