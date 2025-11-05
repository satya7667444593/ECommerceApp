package com.example.ecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.example.ecommerceapp.data.local.dao.FavoriteProductDao
import com.example.ecommerceapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "ecommerceapp_database"
        )
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideFavoriteProductDao(database: AppDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }
}
