package com.example.ecommerceapp.data.remote.model
data class RecommendedProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
) {
    data class Rating(
        val rate: Double,
        val count: Int
    )
}