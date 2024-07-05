// ProductAdapter.kt
package com.example.kdapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(
    private var products: List<Product>,
    private val addToCartCallback: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            addToCartCallback(product)
        }
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
