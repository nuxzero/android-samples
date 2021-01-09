package me.cafecode.android.techcrunchnews.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import me.cafecode.android.techcrunchnews.data.remote.NewsListRemote;
import me.cafecode.android.techcrunchnews.injection.AppModule;
import me.cafecode.android.techcrunchnews.utils.ReadJsonFileHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class NewsListRemoteTest {

    private MockWebServer mMockWebServer;
    private NewsListRemote mRemoteData;
    private CountDownLatch mLock;

    private List<News> mActualNewsList;

    private static final List<News> NEWS_LIST = new ReadJsonFileHelper()
            .getJsonToMock("get_latest_news_list.json", NewsListResponse.class)
            .getNewsList();

    @Before
    public void setUp() throws IOException {

        mMockWebServer = new MockWebServer();

        String baseUrl = mMockWebServer.url("test/").toString();

        mRemoteData = AppModule.provideRemote(baseUrl);

        mLock = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws IOException {
        mMockWebServer.close();
        mActualNewsList = null;
    }

    @Test
    public void loadLatestNewsList_whenRequestSuccessedThenReturnSuccessCallback() throws InterruptedException {
        // Give
        String jsonString = new ReadJsonFileHelper()
                .getJsonFromResources("get_latest_news_list.json");
        mMockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonString));

        // When
        mRemoteData.loadLatestNewsList(new TechCrunchRepositoryDataSource.LoadNewsListCallback() {
            @Override
            public void onNewsListLoaded(List<News> newsList) {
                mActualNewsList = newsList;
                mLock.countDown();
            }

            @Override
            public void onLoadNewsListFailed() {
                fail("Unexpected error!");
                mLock.countDown();
            }
        });
        mLock.await(1000, TimeUnit.MILLISECONDS);

        // Then
        assertThat(mActualNewsList, hasSize(NEWS_LIST.size()));
    }

}
