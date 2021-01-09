package me.cafecode.android.androidmvptesting.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import me.cafecode.android.androidmvptesting.R;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        if (savedInstanceState == null) {
            initFragment(NotesFragment.newInstance());
        }
    }

    private void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.notes_content_layout, fragment)
                .commit();
    }

}
