package com.mobiquity.miniapp.ui.productdetails

import androidx.lifecycle.ViewModel
import com.mobiquity.miniapp.model.entities.Product

class ProductDetailsViewModel(private val product: Product) : ViewModel() {

    fun getProduct() = product
}