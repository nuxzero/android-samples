package me.cafecode.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class GithubRepository(val retrofit: Retrofit) {

    val api: GithubApi = retrofit.create(GithubApi::class.java)
    val TAG = GithubRepository::class.simpleName

    fun retrieveRepoList(success: (List<Repo>) -> Unit, failure: (e: Exception) -> Unit) {
        Log.d(TAG, "GET: repositories")
        api.getRepoList().enqueue(object : Callback<List<Repo>> {

            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                Log.d(TAG, "GET: repositories ${response?.code()}")
                if (response?.isSuccessful == true) {
                    success(response.body()!!)
                } else {
                    failure(ApiException.BadRequestException)
                }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                Log.d(TAG, "GET: repositories failure")
                failure(ApiException.NoInternetConnectionException)
            }
        })
    }

    fun retrieveRepo(owner: String, repoName: String, success: (Repo) -> Unit, failure: (e: Exception) -> Unit) {
        Log.d(TAG, "GET: repos/$owner/$repoName")
        api.getRepo(owner, repoName).enqueue(object : Callback<Repo> {

            override fun onResponse(call: Call<Repo>?, response: Response<Repo>?) {
                Log.d(TAG, "GET: repos/$owner/$repoName ${response?.code()}")
                if (response?.isSuccessful == true) {
                    success(response.body()!!)
                } else {
                    failure(ApiException.BadRequestException)
                }
            }

            override fun onFailure(call: Call<Repo>?, t: Throwable?) {
                Log.d(TAG, "GET: repos/$owner/$repoName failure")
                failure(ApiException.NoInternetConnectionException)
            }
        })
    }

    fun retrieveIssueList(
            owner: String,
            repoName: String,
            state: String = "open" /** all, open, closed */,
            success: (List<Issue>) -> Unit,
            failure: (e: Exception) -> Unit) {
        Log.d(TAG, "GET: issues?state=$state")
        api.getIssueList(owner, repoName, state).enqueue(object : Callback<List<Issue>> {

            override fun onResponse(call: Call<List<Issue>>?, response: Response<List<Issue>>?) {
                Log.d(TAG, "GET: issues ${response?.code()}")
                if (response?.isSuccessful == true) {
                    success(response.body()!!)
                } else {
                    failure(ApiException.BadRequestException)
                }
            }

            override fun onFailure(call: Call<List<Issue>>?, t: Throwable?) {
                Log.d(TAG, "GET: issues failure")
                failure(ApiException.NoInternetConnectionException)
            }
        })
    }
}

sealed class ApiException : Exception() {
    object BadRequestException : ApiException()
    object InternalServerException : ApiException()
    object CannotConnectToServerException : ApiException()
    object NoInternetConnectionException : ApiException()
    object RequestTimeoutException : ApiException()
}