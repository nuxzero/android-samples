package me.cafecode.android.techcrunchnews.data.local;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.cafecode.android.techcrunchnews.data.News;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class NewsListLocal implements NewsListLocalDataSource {

    private static NewsListLocal instance = null;

    private NewsListLocal() {

    }

    public static synchronized NewsListLocal getInstance() {
        if (instance == null) {
            instance = new NewsListLocal();
        }

        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    @Override
    public void saveNews(News news) {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.copyToRealm(news);
        realm.commitTransaction();
    }

    @Override
    public List<News> getNewsList() {
        RealmResults<News> newsRealmResults = getRealm().where(News.class)
                .findAllSorted("publishedAt", Sort.DESCENDING);
        return getRealm().copyFromRealm(newsRealmResults);
    }

    @Override
    public void clearAll() {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

}
