package com.mobiquity.miniapp.ui.cataloglist

import androidx.lifecycle.*
import com.mobiquity.miniapp.model.entities.Category
import com.mobiquity.miniapp.model.entities.Product
import com.mobiquity.miniapp.model.repository.CatalogRepository
import com.mobiquity.miniapp.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogListViewModel @Inject constructor(private val catalogRepository: CatalogRepository) :
    ViewModel() {

    private val categories = MutableLiveData<Result<List<Category>>>()

    private val _navigateToProductDetails = MutableLiveData<Product>()
    val navigateToProductDetails
        get() = _navigateToProductDetails

    fun fetchCatalog() = viewModelScope.launch {
        categories.postValue(Result.Loading())

        val result = catalogRepository.getCatalog()
        if (result.status == Result.Status.SUCCESS && result.data != null) {
            categories.postValue(Result.Success(result.data!!))
        } else if (result.status == Result.Status.ERROR) {
            categories.postValue(Result.Error(result.message))
        }
    }

    fun getCategories(): MutableLiveData<Result<List<Category>>> = categories

    fun onProductClicked(product: Product) {
        _navigateToProductDetails.value = product
    }

    fun onProductDetailsNavigated() {
        _navigateToProductDetails.value = null
    }

}