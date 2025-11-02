package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val uploaderId: String,
    val uploaderName: String,
    val addedAt: Long = System.currentTimeMillis()
)