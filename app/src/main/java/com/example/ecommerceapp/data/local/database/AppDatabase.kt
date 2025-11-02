package com.example.ecommerceapp.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerceapp.data.local.dao.FavoriteProductDao
import com.example.ecommerceapp.data.local.entity.FavoriteProductEntity

@Database(
    entities = [FavoriteProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
}