package me.cafecode.android.techcrunchnews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.data.NewsListResponse;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepositoryDataSource;
import me.cafecode.android.techcrunchnews.news.NewsListContract;
import me.cafecode.android.techcrunchnews.news.NewsListPresenter;
import me.cafecode.android.techcrunchnews.utils.ReadJsonFileHelper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class NewsListPresenterTest {

    private NewsListPresenter mPresenter;

    private static final List<News> NEWS_LIST = new ReadJsonFileHelper()
            .getJsonToMock("get_latest_news_list.json", NewsListResponse.class)
            .getNewsList();

    @Mock
    private NewsListContract.View mView;

    @Mock
    private TechCrunchRepositoryDataSource mRepository;

    @Captor
    private ArgumentCaptor<TechCrunchRepositoryDataSource.LoadNewsListCallback> mLoadNewsListCallback;

    @Before
    public void setUp() {

        mPresenter = new NewsListPresenter(mRepository, mView);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void start_whenStartPresenterAndLoadNewsListFromAPIThenShowProgressBar() {
        // Give empty local data
        when(mRepository.getLatestNewsList()).thenReturn(new ArrayList<News>());

        // When
        mPresenter.start();

        // Then
        verify(mView).showProgressBar(true);
    }


    @Test
    public void start_whenStartPresenterAndLoadNewsListFromAPIThenNotShowProgressBarAndShowNewsListView() {
        // Give
        when(mRepository.getLatestNewsList()).thenReturn(NEWS_LIST);

        // When
        mPresenter.start();

        // Then
        verify(mView).showProgressBar(false);
        verify(mView).showNewsList(NEWS_LIST);
    }

    @Test
    public void start_whenStartPresenterThenShowNewsListFromLocal() {
        // Give
        when(mRepository.getLatestNewsList()).thenReturn(NEWS_LIST);

        // When
        mPresenter.start();

        // Then
        verify(mView).showNewsList(NEWS_LIST);
    }

    @Test
    public void loadNewsList_whenThereAreThreeNewsesThenShowNewsList() {

        // Give
        List<News> newsList = NEWS_LIST;

        // When
        mPresenter.loadNewsList();

        // Callback
        verify(mRepository).getLatestNewsList(mLoadNewsListCallback.capture());
        mLoadNewsListCallback.getValue().onNewsListLoaded(newsList);

        // Then
        verify(mView).showNewsList(newsList);
    }

    @Test
    public void loadNewsList_whenThereAreNoNewsThenShowNoNews() {

        // Give
        List<News> newsList = new ArrayList<>();

        // When
        mPresenter.loadNewsList();

        // Callback
        verify(mRepository).getLatestNewsList(mLoadNewsListCallback.capture());
        mLoadNewsListCallback.getValue().onNewsListLoaded(newsList);

        // Then
        verify(mView).showNoNews();
    }

    @Test
    public void loadNewsList_whenLoadFailedThenShowErrorMessage() {
        // Give

        // When
        mPresenter.loadNewsList();

        // Callback
        verify(mRepository).getLatestNewsList(mLoadNewsListCallback.capture());
        mLoadNewsListCallback.getValue().onLoadNewsListFailed();

        // Then
        verify(mView).showErrorMessage(anyString());
    }
}
