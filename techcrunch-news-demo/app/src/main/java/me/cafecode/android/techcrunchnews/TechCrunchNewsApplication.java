package me.cafecode.android.techcrunchnews;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Natthawut Hemathulin on 5/25/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class TechCrunchNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
