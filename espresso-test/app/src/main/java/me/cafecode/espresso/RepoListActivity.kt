package me.cafecode.espresso

import android.content.Intent
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_repo_list.*

class RepoListActivity : AppCompatActivity() {


    @VisibleForTesting
    val appIdlingResource: AppIdlingResource = AppIdlingResource()

    private val repository: GithubRepository by lazy { GithubRepository(appIdlingResource) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.adapter = RepoListAdapter(null) {
            val intent = Intent(this, RepoDetailActivity::class.java)
            intent.putExtra(RepoDetailActivity.EXTRA_REPO_ID, it.id)
            startActivity(intent)
        }

//        appIdlingResource?.setIdleState(false)
        repository.getRepoListAsync { repoList ->
            (list.adapter as RepoListAdapter).let {
                it.repoList = repoList
                it.notifyDataSetChanged()
            }
//            appIdlingResource?.setIdleState(true)
        }
    }
}
