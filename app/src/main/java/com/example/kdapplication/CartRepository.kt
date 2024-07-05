// CartRepository.kt
package com.example.kdapplication

object CartRepository {
    private val cartItems = mutableListOf<Product>()

    fun addProduct(product: Product) {
        cartItems.add(product)
    }

    fun getCartProducts(): List<Product> {
        return cartItems
    }

    fun clearCart() {
        cartItems.clear()
    }
}
