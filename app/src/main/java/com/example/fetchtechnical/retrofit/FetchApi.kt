package com.example.fetchtechnical.retrofit

import com.example.fetchtechnical.Constants.Companion.FETCH_HIRING_ENDPOINT
import com.example.fetchtechnical.models.ItemModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface FetchApi {

    @GET(FETCH_HIRING_ENDPOINT)
    suspend fun getSampleData(): Response<List<ItemModel>>
}