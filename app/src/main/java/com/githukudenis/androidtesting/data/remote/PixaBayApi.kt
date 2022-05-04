package com.githukudenis.androidtesting.data.remote

import com.githukudenis.androidtesting.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaBayApi {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") query: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}
