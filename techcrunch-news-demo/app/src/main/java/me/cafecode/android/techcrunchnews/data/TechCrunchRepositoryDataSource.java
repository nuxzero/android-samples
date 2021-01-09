package me.cafecode.android.techcrunchnews.data;

import java.util.List;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface TechCrunchRepositoryDataSource {

    List<News> getLatestNewsList();

    void getLatestNewsList(LoadNewsListCallback callback);

    void deleteAllLocal();

    interface LoadNewsListCallback {

        void onNewsListLoaded(List<News> newsList);

        void onLoadNewsListFailed();

    }

}
