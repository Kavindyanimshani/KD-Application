// CheckoutActivity.kt
package com.example.kdapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckoutActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        recyclerView = findViewById(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(CartRepository.getCartProducts())
        recyclerView.adapter = cartAdapter

        val checkoutButton = findViewById<Button>(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            showPaymentSuccessPopup()
        }
    }

    private fun showPaymentSuccessPopup() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Payment Success")
        builder.setMessage("Your payment was successful!")
        builder.setPositiveButton("OK") { _, _ ->
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        builder.show()
    }
}
