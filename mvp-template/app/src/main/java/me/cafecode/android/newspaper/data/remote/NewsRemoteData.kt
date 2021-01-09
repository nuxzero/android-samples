package me.cafecode.android.newspaper.data.remote

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.cafecode.android.newspaper.data.models.News
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsRemoteData(baseUrl: String) : NewsRemoteDataSource {

    private var mApiService: NewsRemoteService

    init {
        val client = OkHttpClient()

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        mApiService = retrofit.create(NewsRemoteService::class.java)
    }

    override fun loadNewses(): Observable<List<News>> {
        return mApiService.getNewses()
                .flatMap { newsesResponse -> Observable.just(newsesResponse.newses) }
    }

}
