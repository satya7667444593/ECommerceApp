package com.example.ecommerceapp.ui.favorites

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.remote.model.Product
import com.example.ecommerceapp.data.repository.AuthRepository
import com.example.ecommerceapp.data.repository.ProductRepository
import com.example.ecommerceapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UploadProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uploadState = MutableStateFlow<Resource<String>?>(null)
    val uploadState: StateFlow<Resource<String>?> = _uploadState.asStateFlow()

    fun uploadProduct(
        title: String,
        description: String,
        price: Double,
        category: String,
        imageUris: List<Uri>
    ) {
        viewModelScope.launch {
            _uploadState.value = Resource.Loading()

            val user = authRepository.getCurrentUserData()
            if (user == null) {
                _uploadState.value = Resource.Error("User not authenticated")
                return@launch
            }

            val product = Product(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                price = price,
                category = category,
                uploaderId = user.id,
                uploaderName = user.name,
                uploaderEmail = user.email
            )

            val result = productRepository.uploadProduct(product, imageUris)
            _uploadState.value = result
        }
    }

    fun resetState() {
        _uploadState.value = null
    }
}