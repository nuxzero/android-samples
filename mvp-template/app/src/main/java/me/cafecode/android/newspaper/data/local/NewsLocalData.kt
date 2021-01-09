package me.cafecode.android.newspaper.data.local

import android.database.sqlite.SQLiteConstraintException
import me.cafecode.android.newspaper.data.models.News

class NewsLocalData(val localDatabase: LocalDatabase) : NewsLocalDataSource {

    override fun loadNewses(): List<News> {
        return localDatabase.newsDao().news
    }

    override fun saveNewses(newses: List<News>) {
        for (news in newses) {
            try {
                localDatabase.newsDao().insertNews(news)
            } catch (e: SQLiteConstraintException) {
                localDatabase.newsDao().updateNews(news)
            }
        }
    }

}
