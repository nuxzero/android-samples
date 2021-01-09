package me.cafecode.android.techcrunchnews.injection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.cafecode.android.techcrunchnews.data.TechCrunchRepository;
import me.cafecode.android.techcrunchnews.data.local.NewsListLocal;
import me.cafecode.android.techcrunchnews.data.remote.NewsListRemote;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class AppModule {

    private static final String API_ENDPOINT = "https://newsapi.org/v1/";

    public static TechCrunchRepository provideRepository() {
        return TechCrunchRepository.getInstance(provideDataLocal(), provideRemote());
    }

    public static NewsListLocal provideDataLocal() {

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        return NewsListLocal.getInstance();
    }

    public static NewsListRemote provideRemote() {
        return NewsListRemote.getInstance(API_ENDPOINT);
    }

    public static NewsListRemote provideRemote(String baseUrl) {
        return NewsListRemote.getInstance(baseUrl);
    }

}
