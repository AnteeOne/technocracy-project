package com.anteeone.coverit.data.network

import com.anteeone.coverit.data.network.dto.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("?method=chart.gettoptracks&format=json")
    suspend fun getCharts(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): ApiResponse
}