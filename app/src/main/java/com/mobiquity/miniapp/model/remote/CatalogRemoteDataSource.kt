package com.mobiquity.miniapp.model.remote

import javax.inject.Inject

class CatalogRemoteDataSource @Inject constructor(
    private val catalogService: CatalogService

) : BaseDataSource() {

    suspend fun getCatalog() = getResult { catalogService.getCatalog() }

}