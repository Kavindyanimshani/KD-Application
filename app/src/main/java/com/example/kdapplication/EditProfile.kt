package com.example.kdapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kdapplication.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users").child(auth.currentUser?.uid ?: "")

        val email = intent.getStringExtra("EMAIL") ?: ""
        val password = intent.getStringExtra("PASSWORD") ?: ""

        // Autofill email and password fields and make them non-editable
        binding.editTextText6.setText(email)
        binding.editTextText6.isEnabled = false
        binding.txtpassword.setText(password)
        binding.txtpassword.isEnabled = false

        loadUserData()

        binding.save.setOnClickListener {
            val phone = binding.editTextText5.text.toString()
            val birthday = binding.editTextText4.text.toString()

            updateUser(phone, birthday)
        }

        binding.btnDelete.setOnClickListener {
            deleteUser()
        }
    }

    private fun loadUserData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    binding.txtusername.setText(user.username)
                    binding.editTextText5.setText(user.phone)
                    binding.editTextText4.setText(user.birthday)
                } else {
                    Toast.makeText(this@EditProfile, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfile, "Error loading user data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser(phone: String, birthday: String) {
        val updates = mapOf(
            "phone" to phone,
            "birthday" to birthday
        )
        database.updateChildren(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUser() {
        database.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Signup::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
