package com.example.kdapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ViewProducts : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_products)

        val productListView: ListView = findViewById(R.id.productListView)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val products = fetchProducts()
                withContext(Dispatchers.Main) {
                    if (products != null) {
                        val productNames = products.map { it.name }
                        val adapter = ArrayAdapter(this@ViewProducts, android.R.layout.simple_list_item_1, productNames)
                        productListView.adapter = adapter

                        productListView.setOnItemClickListener { _, _, position, _ ->
                            val selectedProduct = products[position]
                            val intent = Intent(this@ViewProducts, InduvidualProuct::class.java).apply {
                                putExtra("PRODUCT_ID", selectedProduct.id)
                            }
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@ViewProducts, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ViewProducts, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchProducts(): List<Product>? {
        val request = Request.Builder()
            .url("http://YOUR_SERVER_IP:3000/api/products")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val jsonData = response.body!!.string()
            val gson = Gson()
            val productType = object : TypeToken<List<Product>>() {}.type
            return gson.fromJson(jsonData, productType)
        }
    }

    data class Product(val id: Int, val name: String, val price: Double, val details: String, val image: String)
}
