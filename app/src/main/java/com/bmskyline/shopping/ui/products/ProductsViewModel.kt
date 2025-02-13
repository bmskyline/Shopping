package com.bmskyline.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bmskyline.shopping.repository.model.products.Product
import com.bmskyline.shopping.repository.repo.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {

    val products: LiveData<List<Product>> = productsRepository.getProducts.asLiveData()

    fun refreshProducts() = viewModelScope.launch {
        productsRepository.insertProducts()
    }
}