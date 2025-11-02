package com.example.ecommerceapp.data.remote.api

import com.example.ecommerceapp.data.remote.model.RecommendedProduct
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    @GET("products")
    suspend fun getProducts(): List<RecommendedProduct>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): RecommendedProduct

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<RecommendedProduct>
}