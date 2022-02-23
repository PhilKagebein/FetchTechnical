package com.example.fetchtechnical.repository

import com.example.fetchtechnical.models.ItemModel
import com.example.fetchtechnical.retrofit.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getFetchHiringData(): Response<List<ItemModel>> {
        return RetrofitInstance.api.getSampleData()
    }
}