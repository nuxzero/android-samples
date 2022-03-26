package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var menuListAdapter: MenuListAdapter
    private lateinit var menuListWithTailAdapter: MenuListWithTailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
        menuListAdapter = MenuListAdapter(Menu.menus)
        menuListWithTailAdapter = MenuListWithTailAdapter(Menu.menus)

        binding.recyclerView.apply {
//            adapter = this@MainActivity.menuListAdapter
//            val itemTouchHelper = MenuListTouchHelper(this@MainActivity.menuListAdapter::onItemMove)

            adapter = menuListWithTailAdapter
            val itemTouchHelper = MenuListWithTailTouchHelper(this@MainActivity.menuListWithTailAdapter::onItemMove)

            itemTouchHelper.attachToRecyclerView(this)
        }
    }
}