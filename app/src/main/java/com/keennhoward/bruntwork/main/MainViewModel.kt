package com.keennhoward.bruntwork.main

import androidx.lifecycle.ViewModel
import com.keennhoward.bruntwork.main.MainRepository

class MainViewModel(private val repository: MainRepository):ViewModel() {

    val cart = repository.cart
}