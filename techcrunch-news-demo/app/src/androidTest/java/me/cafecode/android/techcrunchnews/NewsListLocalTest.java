package me.cafecode.android.techcrunchnews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.data.local.NewsListLocal;
import me.cafecode.android.techcrunchnews.injection.AppModule;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

@RunWith(AndroidJUnit4.class)
public class NewsListLocalTest {

    private static final News NEWS_1 = new News();

    private NewsListLocal mLocalData;

    private Realm mRealm;

    @Before
    public void setUp() {

        NEWS_1.setTitle("Test title");

        NewsListLocal.destroyInstance();

        Context context = InstrumentationRegistry.getTargetContext();
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("test.Realm")
                .inMemory()
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(config);
        mLocalData = AppModule.provideDataLocal();
    }

    @After
    public void tearDown() {
        mLocalData.clearAll();
    }

    @Test
    public void saveNews_whenSavedNewsThenRealmShouldHaveNews() {
        // Give

        // When
        mLocalData.saveNews(NEWS_1);

        // Then
        assertNotNull(mRealm.where(News.class).equalTo("title", NEWS_1.getTitle()).findAll());
    }

    @Test
    public void getNewsList_whenThereIsNewsListInLocalThenReturnNewsList() {
        // Give
        mLocalData.saveNews(NEWS_1);

        // When
        List<News> actualNewsList = mLocalData.getNewsList();

        // Then
        assertThat(actualNewsList, hasSize(1));
    }

    @Test
    public void getNewsList_whenThereIsNoNewsListInLocalThenReturnEmptyNewsList() {
        // Give

        // When
        List<News> actualNewsList = mLocalData.getNewsList();

        // Then
        assertThat(actualNewsList, hasSize(0));
    }

    @Test
    public void clearAll_whenThereIsNewsListInLocalThenClearAllNewsList() {
        // Give
         mLocalData.saveNews(NEWS_1);

        // When
        mLocalData.clearAll();

        // Then
        assertThat(mRealm.where(News.class).findAll(), hasSize(0));
    }

}
