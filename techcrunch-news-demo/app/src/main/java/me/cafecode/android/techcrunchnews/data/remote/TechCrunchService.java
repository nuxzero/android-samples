package me.cafecode.android.techcrunchnews.data.remote;

import me.cafecode.android.techcrunchnews.data.NewsListResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface TechCrunchService {

    @GET("articles?source=techcrunch&sortBy=latest&apiKey=691700b86fee41c58f6cee530e91623b")
    Call<NewsListResponse> latestNewsList();

}
