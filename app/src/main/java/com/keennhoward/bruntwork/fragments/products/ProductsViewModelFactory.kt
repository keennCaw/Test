package com.keennhoward.bruntwork.fragments.products

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductsViewModelFactory (private val repository: ProductsRepository,private val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}