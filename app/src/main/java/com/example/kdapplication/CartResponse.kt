package com.example.kdapplication

data class CartResponse(
    val id: Int,
    val userId: String,
    val date: String,
    val items: List<Product>
)
