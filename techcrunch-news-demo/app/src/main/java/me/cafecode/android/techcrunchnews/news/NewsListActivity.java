package me.cafecode.android.techcrunchnews.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import me.cafecode.android.techcrunchnews.R;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        if (savedInstanceState == null) {
            initFragment(NewsListFragment.newInstance());
        }
    }

    private void initFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.news_list_content, fragment)
                .commit();
    }

}
