package me.cafecode.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val database = Room.databaseBuilder(applicationContext, LocalDatabase::class.java, "me.cafecode.room.database").build()

		val gson = GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
				.create()

		val jsonString = classLoader.getResource("get-issue-list.json").readText()

		val type = object: TypeToken<List<Issue>>() {}.type

		val issues = gson.fromJson<List<Issue>>(jsonString, type)
		Log.d("MainActivity", jsonString)
//		database.issueDao().insertAll(issues)
		val roomIssues = database.issueDao().getAll()
		Log.d("MainActivity", roomIssues.first().title)
	}
}
