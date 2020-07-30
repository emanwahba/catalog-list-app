package com.mobiquity.miniapp.model.repository

import com.mobiquity.miniapp.model.remote.CatalogRemoteDataSource
import javax.inject.Inject

class CatalogRepository @Inject constructor(
    private val remoteDataSource: CatalogRemoteDataSource
) {
    suspend fun getCatalog() = remoteDataSource.getCatalog()
}