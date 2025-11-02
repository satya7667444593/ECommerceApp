package com.example.ecommerceapp.data.remote.model
data class Product(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
    val uploaderId: String = "",
    val uploaderName: String = "",
    val uploaderEmail: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val category: String = ""
)