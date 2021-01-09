package me.cafecode.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = DependecyInjection.createGson()
        val retrofit = DependecyInjection.createRetrofit(gson)
        val repo = GithubRepository(retrofit)

        repo.retrieveRepoList(
                success = {
                    Log.d(TAG, "GET: repositories size: ${it.size}")
                },
                failure = {
                    Log.d(TAG, "GET: repositories failure: ${it.message}")
                })

        repo.retrieveRepo(
                "nuxzero",
                "android-data-binding-sample",
                success = {
                    Log.d(TAG, "GET: repos/nuxzero/repo: ${it.id}")
                },
                failure = {
                    Log.d(TAG, "GET: repos/nuxzero/repo failure: ${it.message}")
                })

        repo.retrieveIssueList(owner = "square",
                repoName = "retrofit",
                success = {
                    Log.d(TAG, "GET: issues size: ${it.size}")
                },
                failure = {
                    Log.d(TAG, "GET: issues failure: ${it.message}")
                })
    }
}
