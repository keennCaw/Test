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

class ProductsAdapter(
    private val productData: Product,
    private val application: Application,
    val listener: ItemClickListener
) :
    RecyclerView.Adapter<ProductsViewHolder>() {

    private var productList = productData.products

    private var allProductList: List<Product.ProductData> = productList

    private var filteredList: ArrayList<Product.ProductData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val productBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(productBinding, application, listener)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val productData: Product.ProductData = productList[position]
        holder.bind(productData)
    }


    //Filter Adapter List by Category
    fun showListByCategory(categoryList: List<String>) {
        filteredList.clear()
        loop@ for (category in categoryList) {
            Log.d("category", category)
            when (category) {
                "All" -> {
                    this.productList = allProductList
                    notifyDataSetChanged()
                    return
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

    interface ItemClickListener {
        fun onAddProductClickListener(product: Product.ProductData)
    }

}

class ProductsViewHolder(
    val binding: ItemProductBinding,
    private val application: Application,
    private val listener: ProductsAdapter.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productData: Product.ProductData) {
        binding.itemName.text = productData.name
        binding.itemCategory.text = productData.category
        binding.itemPrice.text = "$ ${productData.price}"
        binding.itemImageView.setBackgroundColor(productData.bgColor.toColorInt())

        //set image from drawable using product id
        val resID =
            application.resources.getIdentifier(productData.id, "drawable", application.packageName)
        val bm: Bitmap = BitmapFactory.decodeResource(application.resources, resID)
        binding.itemImageView.setImageBitmap(Bitmap.createScaledBitmap(bm, 240, 240, false))

        binding.itemAddToCart.setOnClickListener {
            listener.onAddProductClickListener(productData)
        }
    }

}