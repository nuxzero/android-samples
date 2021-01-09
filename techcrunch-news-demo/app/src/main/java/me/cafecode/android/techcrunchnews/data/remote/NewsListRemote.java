package me.cafecode.android.techcrunchnews.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.data.NewsListResponse;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepositoryDataSource;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class NewsListRemote implements NewsListRemoteDataSource {

    private TechCrunchService mApiService;

    private static NewsListRemote instance = null;

    private NewsListRemote(String baseUrl) {
        OkHttpClient client = new OkHttpClient();

        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        mApiService = retrofit.create(TechCrunchService.class);
    }

    public static synchronized NewsListRemote getInstance(String baseUrl) {
        if (instance == null) {
            instance = new NewsListRemote(baseUrl);
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public void loadLatestNewsList(final TechCrunchRepositoryDataSource.LoadNewsListCallback callback) {

        Call<NewsListResponse> call = mApiService.latestNewsList();
        call.enqueue(new Callback<NewsListResponse>() {
            @Override
            public void onResponse(Call<NewsListResponse> call, Response<NewsListResponse> response) {
                if (response.isSuccessful()) {

                    List<News> newsList = response.body().getNewsList();
                    if (newsList != null) {
                        callback.onNewsListLoaded(newsList);
                    } else {
                        callback.onNewsListLoaded(new ArrayList<News>());
                    }
                } else {
                    callback.onLoadNewsListFailed();
                }
            }

            @Override
            public void onFailure(Call<NewsListResponse> call, Throwable t) {
                callback.onLoadNewsListFailed();
            }
        });
    }

}
