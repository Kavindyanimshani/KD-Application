package com.example.kdapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val frockButton = findViewById<Button>(R.id.btnfrock)
        frockButton.setOnClickListener {
            val intent = Intent(this, InduvidualProuct::class.java)
            startActivity(intent)
        }

        val sariButton = findViewById<Button>(R.id.btnsariyes)
        sariButton.setOnClickListener {
            val intent = Intent(this, InduvidualProuct::class.java)
            startActivity(intent)
        }

        val shirtButton = findViewById<Button>(R.id.btnshirt)
        shirtButton.setOnClickListener {
            val intent = Intent(this, InduvidualProuct::class.java)
            startActivity(intent)
        }

        val profileImageView = findViewById<ImageView>(R.id.acc_icon)
        profileImageView.setOnClickListener {
            val intent = Intent(this, Profile::class.java) // Corrected this line
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Handle home navigation
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    true
                }
                R.id.add -> {
                    // Handle add navigation
                    val intent = Intent(this, ProuductView::class.java)
                    startActivity(intent)
                    true
                }
                R.id.cart -> {
                    // Handle cart navigation
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
