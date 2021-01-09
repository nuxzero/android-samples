package me.cafecode.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApi {

    @GET("repositories")
    fun getRepoList(): Call<List<Repo>>

    @GET("repos/{owner}/{repo}")
    fun getRepo(@Path("owner") owner: String, @Path("repo") repoName: String): Call<Repo>

    @GET("repos/{owner}/{repo}/issues")
    fun getIssueList(@Path("owner") owner: String, @Path("repo") repoName: String, @Query("state") state: String): Call<List<Issue>>
}
