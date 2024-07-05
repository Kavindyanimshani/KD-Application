// CartAdapter.kt
package com.example.kdapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CartAdapter(private var cartProducts: List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateProducts(newProducts: List<Product>) {
        cartProducts = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartProducts[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = cartProducts.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productTitle: TextView = itemView.findViewById(R.id.product_title)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)

        fun bind(product: Product) {
            productTitle.text = product.title
            productPrice.text = "$${product.price}"
            Picasso.get().load(product.image).into(productImage)
        }
    }
}
