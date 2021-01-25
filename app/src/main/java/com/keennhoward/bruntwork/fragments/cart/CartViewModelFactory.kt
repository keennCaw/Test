package com.keennhoward.bruntwork.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keennhoward.bruntwork.main.MainViewModel

class CartViewModelFactory(private val repository: CartRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}