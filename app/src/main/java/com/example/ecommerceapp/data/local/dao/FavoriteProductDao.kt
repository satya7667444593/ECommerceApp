package com.example.ecommerceapp.data.local.dao

import androidx.room.*
import com.example.ecommerceapp.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Query("SELECT * FROM favorite_products ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteProductEntity>>

    @Query("SELECT * FROM favorite_products WHERE id = :productId")
    suspend fun getFavoriteById(productId: String): FavoriteProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: FavoriteProductEntity)

    @Delete
    suspend fun deleteFavorite(product: FavoriteProductEntity)

    @Query("DELETE FROM favorite_products WHERE id = :productId")
    suspend fun deleteFavoriteById(productId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE id = :productId)")
    fun isFavorite(productId: String): Flow<Boolean>
}