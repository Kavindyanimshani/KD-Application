// ProductRepository.kt
package com.example.kdapplication

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class ProductRepository {

    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun fetchWomenClothing(): List<Product>? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("https://fakestoreapi.com/products/category/women's clothing")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext null
                }

                val responseBody = response.body?.string() ?: return@withContext null
                val productListType = object : TypeToken<List<Product>>() {}.type
                return@withContext gson.fromJson(responseBody, productListType)
            }
        }
    }

    suspend fun fetchCartProducts(): List<Product>? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("https://fakestoreapi.com/carts/1") // Adjust the URL to your API endpoint
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext null
                }

                val responseBody = response.body?.string() ?: return@withContext null
                val cartResponse = gson.fromJson(responseBody, CartResponse::class.java)
                return@withContext cartResponse.items
            }
        }
    }



}
