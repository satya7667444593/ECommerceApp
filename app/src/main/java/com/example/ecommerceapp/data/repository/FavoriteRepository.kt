package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.local.dao.FavoriteProductDao
import com.example.ecommerceapp.data.local.entity.FavoriteProductEntity
import com.example.ecommerceapp.data.remote.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteProductDao
) {
    fun getAllFavorites(): Flow<List<FavoriteProductEntity>> {
        return favoriteDao.getAllFavorites()
    }

    suspend fun addToFavorites(product: Product) {
        val entity = FavoriteProductEntity(
            id = product.id,
            title = product.title,
            description = product.description,
            price = product.price,
            imageUrl = product.images.firstOrNull() ?: "",
            uploaderId = product.uploaderId,
            uploaderName = product.uploaderName
        )
        favoriteDao.insertFavorite(entity)
    }

    suspend fun removeFromFavorites(productId: String) {
        favoriteDao.deleteFavoriteById(productId)
    }

    fun isFavorite(productId: String): Flow<Boolean> {
        return favoriteDao.isFavorite(productId)
    }

    suspend fun toggleFavorite(product: Product): Boolean {
        val existing = favoriteDao.getFavoriteById(product.id)
        return if (existing != null) {
            favoriteDao.deleteFavoriteById(product.id)
            false // Removed from favorites
        } else {
            addToFavorites(product)
            true // Added to favorites
        }
    }
}