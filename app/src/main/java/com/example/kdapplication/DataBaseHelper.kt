package com.example.kdapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 2

        // User table
        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_EMAIL = "email"

        // Products table
        private const val TABLE_PRODUCTS = "products"
        private const val COLUMN_PRODUCT_ID = "id"
        private const val COLUMN_PRODUCT_NAME = "name"
        private const val COLUMN_PRODUCT_PRICE = "price"
        private const val COLUMN_PRODUCT_DETAILS = "details"
        private const val COLUMN_PRODUCT_IMAGE_RES_ID = "imageResId"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_USERNAME TEXT,
            $COLUMN_PASSWORD TEXT,
            $COLUMN_EMAIL TEXT
        )
    """.trimIndent()
        db?.execSQL(createTableQuery)

        val createProductsTable = """
        CREATE TABLE $TABLE_PRODUCTS (
            $COLUMN_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_PRODUCT_NAME TEXT,
            $COLUMN_PRODUCT_PRICE REAL,
            $COLUMN_PRODUCT_DETAILS TEXT,
            $COLUMN_PRODUCT_IMAGE_RES_ID INTEGER
        )
    """.trimIndent()
        db?.execSQL(createProductsTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun insertUser(
        username: String,
        password: String,
        email: String,
        phone: String,
        birthday: String
    ): Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_EMAIL, email)
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }


    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    fun getAllProducts(): List<Product> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTS", null)
        val products = mutableListOf<Product>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE))
                val details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DETAILS))
                val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE_RES_ID))
                products.add(Product(id, name, price, details, imageResId.toString()))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return products
    }

    fun insertProduct(name: String, price: Double, details: String, imageResId: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_PRODUCT_NAME, name)
            put(COLUMN_PRODUCT_PRICE, price)
            put(COLUMN_PRODUCT_DETAILS, details)
            put(COLUMN_PRODUCT_IMAGE_RES_ID, imageResId)
        }
        return db.insert(TABLE_PRODUCTS, null, contentValues)
    }

    fun deleteUser(username: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_USERNAME = ?", arrayOf(username))
    }

    fun updateUser(username: String, password: String, email: String, phone: String, birthday: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_PASSWORD, password)
            put(COLUMN_EMAIL, email)
            // Add phone and birthday columns if needed
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_USERNAME = ?", arrayOf(username))
    }

    fun getUser(username: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_USERNAME = ?", arrayOf(username), null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val user = User(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                "",  // phone (if exists)
                ""   // birthday (if exists)
            )
            cursor.close()
            user
        } else {
            cursor?.close()
            null
        }
    }

    data class User(val username: String, val password: String, val email: String, val phone: String, val birthday: String)
    data class Product(val id: Int, val name: String, val price: Double, val details: String, val imageResId: String)
}
