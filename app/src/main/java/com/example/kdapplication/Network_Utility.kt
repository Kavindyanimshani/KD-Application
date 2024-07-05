package com.example.kdapplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class Network_Utility {

    private val BASE_URL = "http://YOUR_SERVER_IP:3000/api/products"

    fun fetchProducts(): List<DataBaseHelper.Product>? {
        val client = OkHttpClient()
        val request = Request.Builder().url(BASE_URL).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val jsonData = response.body!!.string()
            val gson = Gson()
            val productType = object : TypeToken<List<DataBaseHelper.Product>>() {}.type
            return gson.fromJson(jsonData, productType)
        }
    }
}