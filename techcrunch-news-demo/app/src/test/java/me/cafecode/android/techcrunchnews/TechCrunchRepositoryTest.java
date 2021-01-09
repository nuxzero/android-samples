package me.cafecode.android.techcrunchnews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.data.NewsListResponse;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepository;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepositoryDataSource;
import me.cafecode.android.techcrunchnews.data.local.NewsListLocalDataSource;
import me.cafecode.android.techcrunchnews.data.remote.NewsListRemoteDataSource;
import me.cafecode.android.techcrunchnews.utils.ReadJsonFileHelper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class TechCrunchRepositoryTest {

    @Mock
    NewsListLocalDataSource localData;

    @Mock
    NewsListRemoteDataSource remoteData;

    @Mock
    TechCrunchRepositoryDataSource.LoadNewsListCallback mLoadNewsListCallback;

    @Captor
    private ArgumentCaptor<TechCrunchRepositoryDataSource.LoadNewsListCallback> mLoadNewsListCallbackCaptor;

    private TechCrunchRepositoryDataSource mRepository = null;

    private static final List<News> NEWS_LIST = new ReadJsonFileHelper()
            .getJsonToMock("get_latest_news_list.json", NewsListResponse.class)
            .getNewsList();

    @Before
    public void setUp() {
        mRepository = TechCrunchRepository.getInstance(localData, remoteData);
    }

    @After
    public void tearDown() {
        TechCrunchRepository.destroyInstance();
    }

    @Test
    public void getLatestNewsList_whenSuccessRequestThenSaveNewsListToLocal() {
        // Give

        // When
        mRepository.getLatestNewsList(mLoadNewsListCallback);

        // Callback
        verify(remoteData).loadLatestNewsList(mLoadNewsListCallbackCaptor.capture());
        mLoadNewsListCallbackCaptor.getValue().onNewsListLoaded(NEWS_LIST);

        // Then
        verify(localData, times(NEWS_LIST.size())).saveNews(any(News.class));
    }

    @Test
    public void getLatestNewsList_whenSuccessRequestThenReturnNewsListFromLocal() {
        // Give

        // When
        mRepository.getLatestNewsList(mLoadNewsListCallback);

        // Callback
        verify(remoteData).loadLatestNewsList(mLoadNewsListCallbackCaptor.capture());
        mLoadNewsListCallbackCaptor.getValue().onNewsListLoaded(NEWS_LIST);

        // Then
        verify(localData).getNewsList();
    }


    @Test
    public void getLatestNewsList_whenLoadFailedThenReturnSuccessCallback() {
        // Give

        // When
        mRepository.getLatestNewsList(mLoadNewsListCallback);

        // Failure callback
        verify(remoteData).loadLatestNewsList(mLoadNewsListCallbackCaptor.capture());
        mLoadNewsListCallbackCaptor.getValue().onNewsListLoaded(ArgumentMatchers.<List<News>>any());

        // Then
        verify(mLoadNewsListCallback).onNewsListLoaded(ArgumentMatchers.<List<News>>any());
    }

    @Test
    public void getLatestNewsList_whenLoadFailedThenReturnFailureCallback() {
        // Give

        // When
        mRepository.getLatestNewsList(mLoadNewsListCallback);

        // Failure callback
        verify(remoteData).loadLatestNewsList(mLoadNewsListCallbackCaptor.capture());
        mLoadNewsListCallbackCaptor.getValue().onLoadNewsListFailed();

        // Then
        verify(mLoadNewsListCallback).onLoadNewsListFailed();
    }

    @Test
    public void getLatestNewsList_whenThereIsLocalNewsThenReturnNewsList() {
        // Give
        when(localData.getNewsList()).thenReturn(NEWS_LIST);

        // When
        List<News> actualNewsList = mRepository.getLatestNewsList();

        // Then
        assertEquals(NEWS_LIST, actualNewsList);
    }

    @Test
    public void getLatestNewsList_whenThereIsNoLocalNewsThenReturnEmptyList() {
        // Give
        when(localData.getNewsList()).thenReturn(new ArrayList<News>());

        // When
        List<News> actualNewsList = mRepository.getLatestNewsList();

        // Then
        assertTrue(actualNewsList.isEmpty());
    }

}
