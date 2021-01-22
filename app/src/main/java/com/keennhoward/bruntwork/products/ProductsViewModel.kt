package com.keennhoward.bruntwork.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keennhoward.bruntwork.room.CartProductModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: ProductsRepository, application: Application) :
    AndroidViewModel(application) {

    val products = repository.getAllProducts(getApplication())

    fun insert(cartProductModel: CartProductModel): Job = viewModelScope.launch {
        repository.insert(cartProductModel)
    }

}