package me.cafecode.android.techcrunchnews.data.remote;

import me.cafecode.android.techcrunchnews.data.TechCrunchRepositoryDataSource;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface NewsListRemoteDataSource {

    void loadLatestNewsList(TechCrunchRepositoryDataSource.LoadNewsListCallback callback);

}
