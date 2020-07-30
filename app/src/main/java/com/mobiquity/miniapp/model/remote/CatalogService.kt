package com.mobiquity.miniapp.model.remote

import com.mobiquity.miniapp.model.entities.Category
import retrofit2.Response
import retrofit2.http.GET

interface CatalogService {

    @GET("/")
    suspend fun getCatalog(): Response<List<Category>>
}