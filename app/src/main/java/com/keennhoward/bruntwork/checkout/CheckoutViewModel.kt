package com.keennhoward.bruntwork.checkout

import androidx.lifecycle.ViewModel

class CheckoutViewModel (private val repository: CheckoutRepository): ViewModel() {
    val cart = repository.cart
}