package me.cafecode.espresso

import android.os.Handler
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.cafecode.espresso.models.Repo
import java.text.SimpleDateFormat
import java.util.*


class GithubRepository(private val idlingResource: AppIdlingResource? = null) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val gson = GsonBuilder()
            .setDateFormat(dateFormat.toPattern())
            .create()

    fun getRepoList(): List<Repo> {
        val json = this::class.java.classLoader!!.getResource("get-repositories.json").readText()
        return gson.fromJson<List<Repo>>(json, object : TypeToken<List<Repo>>() {}.type)
    }

    fun getRepoListAsync(callback: (result: List<Repo>) -> Unit) {
//        Handler ().postDelayed({
//            object : AsyncTask<Void, Void, Void>() {
//                override fun doInBackground(vararg p0: Void?): Void? {
//                    val list = getRepoList()
//                    callback(list)
//                    return null
//                }
//            }.execute()
//        }, 3000)

        idlingResource?.setIdleState(false)

        Handler().postDelayed({
            val list = getRepoList()
            callback(list)

            idlingResource?.setIdleState(true)
        }, 1000)
    }

    fun searchRepoList(query: String): List<Repo> {
        return getRepoList().filter { it.fullName!!.contains(query) }
    }

    fun searchRepoListAsync(query: String, callback: (result: List<Repo>) -> Unit) {
        Handler().postDelayed({
            val list = searchRepoList(query)
            callback(list)
        }, 1000)
    }

    fun getRepo(repoId: Int): Repo? {
        return getRepoList().first { it.id == repoId }
    }

    fun getRepoAsync(repoId: Int, callback: (result: Repo?) -> Unit) {
        idlingResource?.setIdleState(false)

        Handler().postDelayed({
            val repo = getRepo(repoId)
            callback(repo)

            idlingResource?.setIdleState(true)
        }, 1000)
    }
}
