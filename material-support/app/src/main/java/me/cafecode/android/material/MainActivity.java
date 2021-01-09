package me.cafecode.android.material;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView mList;
    String[] menu = {"Typography", "Buttons"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, menu));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            startActivity(new Intent(this, TypographyActivity.class));
        } else if (position == 1) {
            startActivity(new Intent(this, ButtonsActivity.class));
        }
    }
}
