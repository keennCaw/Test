package com.keennhoward.bruntwork.products

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.databinding.ItemProductBinding
import com.keennhoward.bruntwork.model.Product

class ProductsAdapter(private val products: Product, private val application: Application) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val productBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(productBinding, application)
    }

    override fun getItemCount(): Int = products.products.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val productData: Product.ProductData = products.products[position]
        holder.bind(productData)
    }
}

class MyViewHolder(val binding: ItemProductBinding, private val application: Application) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productData: Product.ProductData) {
        binding.itemName.text = productData.name
        binding.itemCategory.text = productData.category
        binding.itemPrice.text = "$ ${productData.price}"
        binding.itemImageView.setBackgroundColor(productData.bgColor.toColorInt())

        val resID =
            application.resources.getIdentifier(productData.id, "drawable", application.packageName)

        val bm: Bitmap = BitmapFactory.decodeResource(application.resources, resID)


        binding.itemImageView.setImageBitmap(Bitmap.createScaledBitmap(bm, 240,240, false))
        //binding.itemImageView.setImageResource(resID)
    }
}