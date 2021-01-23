package com.keennhoward.bruntwork.products

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.keennhoward.bruntwork.databinding.ItemProductBinding
import com.keennhoward.bruntwork.model.Product
import java.util.ArrayList

class ProductsAdapter(private val productData: Product, private val application: Application) :
    RecyclerView.Adapter<MyViewHolder>() {

    private var productList = productData.products

    private var allProductList: List<Product.ProductData> = productList

    private var filteredList: ArrayList<Product.ProductData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val productBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(productBinding, application)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val productData: Product.ProductData = productList[position]
        holder.bind(productData)
    }


    //Filter Adapter List by Category
    fun showListByCategory(categoryList: List<String>) {
        filteredList.clear()
        loop@ for(category in categoryList){
            Log.d("category",category)
            when (category) {
                "All" -> {
                    this.productList = allProductList
                    break@loop
                }
                "Tees" -> {
                    this.filteredList.addAll(allProductList.filter { it.category == "Tee" })
                }
                "Jackets" -> {
                    this.filteredList.addAll(allProductList.filter { it.category == "Jacket" })
                }
                "Blazers" -> {
                    this.filteredList.addAll(allProductList.filter { it.category == "Blazer" })
                }
                else -> {
                    this.productList = allProductList
                    break@loop
                }
            }
        }
        this.productList = filteredList.sortedBy { it.id }
        notifyDataSetChanged()
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
        binding.itemImageView.setImageBitmap(Bitmap.createScaledBitmap(bm, 240, 240, false))
    }
}