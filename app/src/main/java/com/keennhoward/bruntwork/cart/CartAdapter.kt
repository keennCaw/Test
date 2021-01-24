package com.keennhoward.bruntwork.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keennhoward.bruntwork.databinding.ItemCartBinding
import com.keennhoward.bruntwork.db.room.CartProductModel

class CartAdapter(val listener: CartItemClickListener) : ListAdapter<CartProductModel,CartViewHolder>(CartDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val cartBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(cartBinding, listener)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

//cart item click listener interface to pass cartItem onClick
interface CartItemClickListener {
    fun onDeleteCartItemClickListener(cartItem: CartProductModel)
}


class CartViewHolder(
    private val binding: ItemCartBinding,
    private val listener: CartItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cartItem: CartProductModel) {
        binding.cartItemProductName.text = cartItem.name
        binding.cartItemPrice.text = cartItem.price
        binding.cartItemBg.setBackgroundColor(cartItem.bgColor.toColorInt())

        binding.cartItemDelete.setOnClickListener {
            listener.onDeleteCartItemClickListener(cartItem)
        }
    }
}

//DiffUtil for CartAdapter ListAdapter
class CartDiffUtil: DiffUtil.ItemCallback<CartProductModel>(){
    override fun areItemsTheSame(oldItem: CartProductModel, newItem: CartProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartProductModel, newItem: CartProductModel): Boolean {
        return oldItem == newItem
    }
}