package me.cafecode.android.techcrunchnews.data;

import java.util.ArrayList;
import java.util.List;

import me.cafecode.android.techcrunchnews.data.local.NewsListLocalDataSource;
import me.cafecode.android.techcrunchnews.data.remote.NewsListRemoteDataSource;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class TechCrunchRepository implements TechCrunchRepositoryDataSource {

    private NewsListRemoteDataSource mRemoteData;

    private NewsListLocalDataSource mLocalData;

    private static TechCrunchRepository instance = null;

    private TechCrunchRepository(NewsListLocalDataSource dataLocal, NewsListRemoteDataSource remoteData) {
        mLocalData = dataLocal;
        mRemoteData = remoteData;
    }

    public static synchronized TechCrunchRepository getInstance(NewsListLocalDataSource dataLocal, NewsListRemoteDataSource remoteData) {
        if (instance == null) {
            instance = new TechCrunchRepository(dataLocal, remoteData);
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public List<News> getLatestNewsList() {
        return mLocalData.getNewsList();
    }

    @Override
    public void getLatestNewsList(final LoadNewsListCallback callback) {

        mRemoteData.loadLatestNewsList(new LoadNewsListCallback() {

            @Override
            public void onNewsListLoaded(List<News> newsList) {
                if (newsList != null) {
                    // Clear old local data
                    mLocalData.clearAll();

                    // Save to local data
                    for (News news : newsList) {
                        mLocalData.saveNews(news);
                    }

                    callback.onNewsListLoaded(mLocalData.getNewsList());
                } else {
                    callback.onNewsListLoaded(new ArrayList<News>());
                }
            }

            @Override
            public void onLoadNewsListFailed() {
                callback.onLoadNewsListFailed();
            }

        });
    }

    @Override
    public void deleteAllLocal() {
        mLocalData.clearAll();
    }

}
