package com.example.ecommerceapp.data.remote.firebase


import android.net.Uri
import com.example.ecommerceapp.data.remote.model.Product
import com.example.ecommerceapp.util.Constants
import com.example.ecommerceapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseProductManager @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    suspend fun uploadProduct(
        product: Product,
        imageUris: List<Uri>
    ): Resource<String> {
        return try {
            // Upload images to Firebase Storage
            val imageUrls = uploadImages(product.id, imageUris)

            // Create product with image URLs
            val productWithImages = product.copy(images = imageUrls)

            // Save to Firestore
            firestore.collection(Constants.PRODUCTS_COLLECTION)
                .document(product.id)
                .set(productWithImages)
                .await()

            Resource.Success(product.id)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to upload product")
        }
    }

    private suspend fun uploadImages(productId: String, uris: List<Uri>): List<String> {
        val imageUrls = mutableListOf<String>()

        uris.forEachIndexed { index, uri ->
            val ref = storage.reference
                .child("${Constants.STORAGE_PATH_PRODUCTS}/$productId/image_$index.jpg")

            ref.putFile(uri).await()
            val downloadUrl = ref.downloadUrl.await()
            imageUrls.add(downloadUrl.toString())
        }

        return imageUrls
    }

    fun getAllProducts(): Flow<Resource<List<Product>>> = callbackFlow {
        trySend(Resource.Loading())

        val listener = firestore.collection(Constants.PRODUCTS_COLLECTION)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Failed to fetch products"))
                    return@addSnapshotListener
                }

                val products = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Product::class.java)
                } ?: emptyList()

                trySend(Resource.Success(products))
            }

        awaitClose { listener.remove() }
    }

    suspend fun getProductById(productId: String): Resource<Product> {
        return try {
            val doc = firestore.collection(Constants.PRODUCTS_COLLECTION)
                .document(productId)
                .get()
                .await()

            val product = doc.toObject(Product::class.java)
                ?: return Resource.Error("Product not found")

            Resource.Success(product)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch product")
        }
    }

    suspend fun searchProducts(query: String): Resource<List<Product>> {
        return try {
            val snapshot = firestore.collection(Constants.PRODUCTS_COLLECTION)
                .get()
                .await()

            val products = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Product::class.java)
            }.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true)
            }

            Resource.Success(products)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Search failed")
        }
    }

    suspend fun getProductsByCategory(category: String): Resource<List<Product>> {
        return try {
            val snapshot = firestore.collection(Constants.PRODUCTS_COLLECTION)
                .whereEqualTo("category", category)
                .get()
                .await()

            val products = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Product::class.java)
            }

            Resource.Success(products)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch products by category")
        }
    }
}
