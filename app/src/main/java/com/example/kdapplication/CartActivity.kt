// CartActivity.kt
package com.example.kdapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cartProducts = CartRepository.getCartProducts()

        if (cartProducts.isNotEmpty()) {
            cartAdapter = CartAdapter(cartProducts)
            recyclerView.adapter = cartAdapter
        } else {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
        }
    }
}
