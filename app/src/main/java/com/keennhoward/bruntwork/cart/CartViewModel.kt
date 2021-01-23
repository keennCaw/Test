package com.keennhoward.bruntwork.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keennhoward.bruntwork.products.ProductsRepository
import com.keennhoward.bruntwork.room.CartProductModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository):ViewModel() {

    val cart = repository.cart

    fun delete(cartProductModel: CartProductModel): Job = viewModelScope.launch {
        repository.delete(cartProductModel)
    }
}