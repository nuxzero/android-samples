package me.cafecode.android.techcrunchnews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.data.NewsListResponse;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepository;
import me.cafecode.android.techcrunchnews.data.local.NewsListLocal;
import me.cafecode.android.techcrunchnews.data.remote.NewsListRemote;
import me.cafecode.android.techcrunchnews.injection.AppModule;
import me.cafecode.android.techcrunchnews.news.NewsListActivity;
import me.cafecode.android.techcrunchnews.utils.ReadJsonFileHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewsListScreenTest {

    private MockWebServer mMockWebServer;
    private Context mContext = InstrumentationRegistry.getTargetContext();

    private static final String JSON = new ReadJsonFileHelper().getJsonFromResources("get_latest_news_list.json");

    private static final List<News> NEWS_LIST = new ReadJsonFileHelper()
            .getJsonToMock("get_latest_news_list.json", NewsListResponse.class)
            .getNewsList();

    @Rule
    public ActivityTestRule<NewsListActivity> activityTestRule = new ActivityTestRule<NewsListActivity>(NewsListActivity.class) {

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            TechCrunchRepository.destroyInstance();
            NewsListLocal.destroyInstance();
            NewsListRemote.destroyInstance();

            // Inject remote data
            mMockWebServer = new MockWebServer();
            String baseUrl = mMockWebServer.url("test/").toString();
            AppModule.provideRemote(baseUrl);

            // Inject local data
            Realm.init(mContext);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("androidTest.Realm")
                    .inMemory()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
            AppModule.provideDataLocal();

            AppModule.provideRepository();
        }
    };

    @After
    public void tearDown() {

        NewsListLocal.getInstance().clearAll();

        TechCrunchRepository.destroyInstance();
        NewsListLocal.destroyInstance();
        NewsListRemote.destroyInstance();
    }

    @Test
    public void whenActivityStartAndThereIsNoLocalDataThenShowProgressBar() {

        mMockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(JSON));

        News news = NEWS_LIST.get(9);
        onView(withId(R.id.news_list_view)).perform(scrollTo(hasDescendant(withText(news.getTitle()))));

        onView(withText(news.getTitle())).check(matches(isDisplayed()));
    }

}
