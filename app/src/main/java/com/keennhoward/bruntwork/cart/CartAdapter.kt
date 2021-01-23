package com.keennhoward.bruntwork.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.keennhoward.bruntwork.databinding.ItemCartBinding
import com.keennhoward.bruntwork.room.CartProductModel

class CartAdapter():RecyclerView.Adapter<CartViewHolder>() {

    var cartProducts = ArrayList<CartProductModel>()

    fun setListData(products: ArrayList<CartProductModel>){
        this.cartProducts = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val cartBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(cartBinding)
    }

    override fun getItemCount(): Int = cartProducts.size


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartProducts[position]
        holder.bind(cartItem)
    }
}

class CartViewHolder(val binding: ItemCartBinding ): RecyclerView.ViewHolder(binding.root){

    fun bind(cartProductModel: CartProductModel){
        binding.cartItemProductName.text = cartProductModel.name
        binding.cartItemPrice.text = cartProductModel.price
        binding.cartItemBg.setBackgroundColor(cartProductModel.bgColor.toColorInt())
    }
}