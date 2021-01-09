package me.cafecode.arch_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.cafecode.arch_test.models.Repo


class RepoViewModel(val githubRepository: GithubRepository) : ViewModel() {

    val repoList = MutableLiveData<List<Repo>>()
    val repo = MutableLiveData<Repo>()

    fun loadRepoList() {
        githubRepository.getRepoListAsync { repoList.postValue(it) }
    }

    fun loadRepo(repoId:Int) {
        githubRepository.getRepoAsync(repoId) {
            repo.postValue(it)
        }
    }

    class Factory(val githubRepository: GithubRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepoViewModel(githubRepository) as T
        }

    }
}

