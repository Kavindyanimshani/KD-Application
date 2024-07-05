package com.example.kdapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Check if the user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, redirect to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // No user is signed in, redirect to Login activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        finish()
    }
}
