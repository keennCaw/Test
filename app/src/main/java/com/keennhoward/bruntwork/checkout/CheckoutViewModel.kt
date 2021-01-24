package com.keennhoward.bruntwork.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keennhoward.bruntwork.db.room.CartProductModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CheckoutViewModel (private val repository: CheckoutRepository): ViewModel() {
    val cart = repository.cart

    fun clearCart(): Job = viewModelScope.launch {
        repository.clearCart()
    }

}