package com.example.ecommerceapp.ui.productdetail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.remote.model.Product
import com.example.ecommerceapp.data.repository.FavoriteRepository
import com.example.ecommerceapp.data.repository.ProductRepository
import com.example.ecommerceapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: String = savedStateHandle.get<String>("productId") ?: ""

    private val _productState = MutableStateFlow<Resource<Product>>(Resource.Loading())
    val productState: StateFlow<Resource<Product>> = _productState.asStateFlow()

    val isFavorite: Flow<Boolean> = favoriteRepository.isFavorite(productId)

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _productState.value = Resource.Loading()
            _productState.value = productRepository.getProductById(productId)
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(product)
        }
    }
}