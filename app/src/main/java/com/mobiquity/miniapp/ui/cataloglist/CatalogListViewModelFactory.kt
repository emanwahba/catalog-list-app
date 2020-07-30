package com.mobiquity.miniapp.ui.cataloglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobiquity.miniapp.model.repository.CatalogRepository

class CatalogListViewModelFactory(private val catalogRepository: CatalogRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogListViewModel::class.java)) {
            return CatalogListViewModel(catalogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}