package me.cafecode.espresso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_repo_detail.*

class RepoDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPO_ID = "me.cafecode.repo.detail.id"
    }

    val appIdlingResource: AppIdlingResource = AppIdlingResource()

    private val repository: GithubRepository by lazy { GithubRepository(appIdlingResource) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)

        val repoId = intent.getIntExtra(EXTRA_REPO_ID, 0)

        if (repoId == 0) {
            throw IllegalArgumentException("Argument not found: $EXTRA_REPO_ID")
        }

        val repo = repository.getRepo(repoId)
                ?: throw IllegalArgumentException("Repo not found with id: $repoId")

        repository.getRepoAsync(repoId) { result ->
            repo_name.text = result?.fullName
            repo_description.text = result?.description
            result?.owner?.let {
                owner_name.text = it.login
            }
        }
    }
}
