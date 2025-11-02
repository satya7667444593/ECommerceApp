package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.local.dao.FavoriteProductDao
import com.example.ecommerceapp.data.remote.api.FakeStoreApi
import com.example.ecommerceapp.data.remote.firebase.FirebaseAuthManager
import com.example.ecommerceapp.data.remote.firebase.FirebaseProductManager
import com.example.ecommerceapp.data.repository.AuthRepository
import com.example.ecommerceapp.data.repository.FavoriteRepository
import com.example.ecommerceapp.data.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthManager(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirebaseAuthManager {
        return FirebaseAuthManager(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseProductManager(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): FirebaseProductManager {
        return FirebaseProductManager(firestore, storage)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authManager: FirebaseAuthManager
    ): AuthRepository {
        return AuthRepository(authManager)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productManager: FirebaseProductManager,
        fakeStoreApi: FakeStoreApi
    ): ProductRepository {
        return ProductRepository(productManager, fakeStoreApi)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDao: FavoriteProductDao
    ): FavoriteRepository {
        return FavoriteRepository(favoriteDao)
    }
}