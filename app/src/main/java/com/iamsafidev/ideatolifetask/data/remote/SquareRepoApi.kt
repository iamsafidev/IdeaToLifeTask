package com.iamsafidev.ideatolifetask.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface SquareRepoApi {
    @GET("orgs/square/repos")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<SquareRepoDto>


    companion object  {
        const val BASE_URL = "https://api.github.com/"
    }
}