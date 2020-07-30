package com.mobiquity.miniapp.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobiquity.miniapp.model.entities.Product

class ProductDetailsViewModelFactory(private val product: Product) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}