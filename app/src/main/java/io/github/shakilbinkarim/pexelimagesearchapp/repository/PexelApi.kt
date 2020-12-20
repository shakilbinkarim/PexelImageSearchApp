package io.github.shakilbinkarim.pexelimagesearchapp.repository

import io.github.shakilbinkarim.pexelimagesearchapp.BuildConfig
import io.github.shakilbinkarim.pexelimagesearchapp.model.PexelResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelApi {

    companion object{
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val CLIENT_ID = BuildConfig.PEXEL_ACCESS_KEY
    }

    @Headers("Authorization: $CLIENT_ID")
    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PexelResponse

}