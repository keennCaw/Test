package com.keennhoward.bruntwork.products

import android.app.Application
import com.google.gson.Gson
import com.keennhoward.bruntwork.model.Product
import com.keennhoward.bruntwork.model.Utils
import com.keennhoward.bruntwork.db.room.CartDAO
import com.keennhoward.bruntwork.db.room.CartProductModel

class ProductsRepository(private val dao: CartDAO) {

    val cart = dao.getCart()

    suspend fun insert(cartProductModel: CartProductModel){
        dao.insert(cartProductModel)
    }

    fun getAllProducts(application:Application):Product {
        val jsonFileString = Utils.getJsonDataFromAsset(application, "products.json")
        val gson = Gson()
        return gson.fromJson(jsonFileString, Product::class.java)
    }

}