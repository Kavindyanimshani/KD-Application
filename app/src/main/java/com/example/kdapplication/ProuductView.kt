// ProuductView.kt
package com.example.kdapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProuductView : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productRepository = ProductRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prouduct_view)

        val backButton = findViewById<ImageView>(R.id.pback)
        backButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(listOf()) { product ->
            CartRepository.addProduct(product)
            Toast.makeText(this, "${product.title} added to cart", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = productAdapter

        fetchProducts()

        val goToCheckoutButton = findViewById<Button>(R.id.goToCheckoutButton)
        goToCheckoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val products = productRepository.fetchWomenClothing()
                if (products != null) {
                    productAdapter.updateProducts(products)
                } else {
                    Toast.makeText(this@ProuductView, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProuductView, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
