package com.example.kdapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class InduvidualProuct : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_induvidual_prouct)

        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        if (productId != -1) {
            fetchProductDetails(productId)
        }
    }

    private fun fetchProductDetails(productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val product = fetchProductById(productId)
                withContext(Dispatchers.Main) {
                    if (product != null) {
                        displayProductDetails(product)
                    }
                }
            } catch (e: IOException) {
                // Handle error
            }
        }
    }

    private fun fetchProductById(productId: Int): Product? {
        val request = Request.Builder()
            .url("http://YOUR_SERVER_IP:3000/api/products/$productId")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val jsonData = response.body!!.string()
            val gson = Gson()
            return gson.fromJson(jsonData, Product::class.java)
        }
    }

    private fun displayProductDetails(product: Product) {
        val productName: TextView = findViewById(R.id.textView4)
        val productPrice: TextView = findViewById(R.id.textView5)
        val productDetails: TextView = findViewById(R.id.textView7)
        val productImage: ImageView = findViewById(R.id.imageView12)

        productName.text = product.name
        productPrice.text = product.price.toString()
        productDetails.text = product.details
        Picasso.get().load(product.image).into(productImage)  // Using Picasso to load images
    }

    data class Product(val id: Int, val name: String, val price: Double, val details: String, val image: String)
}
