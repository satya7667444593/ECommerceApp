package com.example.ecommerceapp.data.repository
import android.net.Uri
import com.example.ecommerceapp.data.remote.api.FakeStoreApi
import com.example.ecommerceapp.data.remote.firebase.FirebaseProductManager
import com.example.ecommerceapp.data.remote.model.Product
import com.example.ecommerceapp.data.remote.model.RecommendedProduct
import com.example.ecommerceapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productManager: FirebaseProductManager,
    private val fakeStoreApi: FakeStoreApi
) {
    suspend fun uploadProduct(product: Product, imageUris: List<Uri>): Resource<String> {
        return productManager.uploadProduct(product, imageUris)
    }

    fun getAllProducts(): Flow<Resource<List<Product>>> {
        return productManager.getAllProducts()
    }

    suspend fun getProductById(productId: String): Resource<Product> {
        return productManager.getProductById(productId)
    }

    suspend fun searchProducts(query: String): Resource<List<Product>> {
        return productManager.searchProducts(query)
    }

    suspend fun getProductsByCategory(category: String): Resource<List<Product>> {
        return productManager.getProductsByCategory(category)
    }

    // Recommended products from FakeStore API
    suspend fun getRecommendedProducts(): Resource<List<RecommendedProduct>> {
        return try {
            val products = fakeStoreApi.getProducts()
            Resource.Success(products)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch recommended products")
        }
    }
}