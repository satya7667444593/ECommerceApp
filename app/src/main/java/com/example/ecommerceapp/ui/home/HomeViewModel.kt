package com.example.ecommerceapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.remote.model.Product
import com.example.ecommerceapp.data.remote.model.RecommendedProduct
import com.example.ecommerceapp.data.remote.model.User
import com.example.ecommerceapp.data.repository.AuthRepository
import com.example.ecommerceapp.data.repository.ProductRepository
import com.example.ecommerceapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _productsState = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val productsState: StateFlow<Resource<List<Product>>> = _productsState.asStateFlow()

    private val _recommendedProductsState = MutableStateFlow<Resource<List<RecommendedProduct>>>(Resource.Loading())
    val recommendedProductsState: StateFlow<Resource<List<RecommendedProduct>>> = _recommendedProductsState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        loadProducts()
        loadRecommendedProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { result ->
                _productsState.value = result
            }
        }
    }

    private fun loadRecommendedProducts() {
        viewModelScope.launch {
            _recommendedProductsState.value = productRepository.getRecommendedProducts()
        }
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            loadProducts()
            return
        }

        viewModelScope.launch {
            _productsState.value = Resource.Loading()
            val result = productRepository.searchProducts(query)
            _productsState.value = result
        }
    }

    fun filterByCategory(category: String?) {
        _selectedCategory.value = category

        if (category == null) {
            loadProducts()
            return
        }

        viewModelScope.launch {
            _productsState.value = Resource.Loading()
            val result = productRepository.getProductsByCategory(category)
            _productsState.value = result
        }
    }

    fun refreshProducts() {
        loadProducts()
    }

    suspend fun getCurrentUser(): User? {
        return authRepository.getCurrentUserData()
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}