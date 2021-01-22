package com.keennhoward.bruntwork.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keennhoward.bruntwork.products.ProductsViewModel

class MainViewModelFactory(private val repository: MainRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}