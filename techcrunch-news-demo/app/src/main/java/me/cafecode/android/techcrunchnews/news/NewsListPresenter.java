package me.cafecode.android.techcrunchnews.news;

import java.util.List;

import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepositoryDataSource;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class NewsListPresenter implements NewsListContract.Presenter {

    private NewsListContract.View mView;
    private TechCrunchRepositoryDataSource mRepository;

    public NewsListPresenter(TechCrunchRepositoryDataSource repository, NewsListContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void start() {
        loadNewsListFromLocal();
        loadNewsList();
    }

    @Override
    public void loadNewsListFromLocal() {
        List<News> newsList = mRepository.getLatestNewsList();
        if (!newsList.isEmpty()) {
            mView.showNewsList(newsList);
        }
    }

    @Override
    public void loadNewsList() {

        if (mRepository.getLatestNewsList().isEmpty()) {
            mView.showProgressBar(true);
        } else {
            mView.showProgressBar(false);
        }

        mRepository.getLatestNewsList(new TechCrunchRepositoryDataSource.LoadNewsListCallback() {
            @Override
            public void onNewsListLoaded(List<News> newsList) {
                mView.showProgressBar(false);
                if (newsList.isEmpty()) {
                    mView.showNoNews();
                } else {
                    mView.showNewsList(newsList);
                }
            }

            @Override
            public void onLoadNewsListFailed() {
                mView.showProgressBar(false);
                mView.showErrorMessage("Cannot load news.");
            }
        });
    }

}
