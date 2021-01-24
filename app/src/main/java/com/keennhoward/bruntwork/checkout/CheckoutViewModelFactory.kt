package com.keennhoward.bruntwork.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keennhoward.bruntwork.cart.CartViewModel

class CheckoutViewModelFactory (private val repository: CheckoutRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}