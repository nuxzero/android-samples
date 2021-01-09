package me.cafecode.android.techcrunchnews.news;

import java.util.List;

import me.cafecode.android.techcrunchnews.BasePresenter;
import me.cafecode.android.techcrunchnews.BaseView;
import me.cafecode.android.techcrunchnews.data.News;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface NewsListContract {

    interface View extends BaseView<Presenter> {

        void showProgressBar(boolean isShow);

        void showNewsList(List<News> newsList);

        void showNoNews();

        void showErrorMessage(String errorMessage);
    }

    interface Presenter extends BasePresenter {

        void loadNewsListFromLocal();

        void loadNewsList();

    }

}
