package me.cafecode.android.techcrunchnews.data.local;

import java.util.List;

import me.cafecode.android.techcrunchnews.data.News;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface NewsListLocalDataSource {

    void saveNews(News news);

    List<News> getNewsList();

    void clearAll();

}
