package com.keennhoward.bruntwork.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keennhoward.bruntwork.db.room.CartProductModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository):ViewModel() {

    val cart = repository.cart

    fun delete(cartProductModel: CartProductModel): Job = viewModelScope.launch {
        repository.delete(cartProductModel)
    }
}