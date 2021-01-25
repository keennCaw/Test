package com.keennhoward.bruntwork.util

import android.content.Context
import com.keennhoward.bruntwork.fragments.checkout.model.OrderModel
import com.keennhoward.bruntwork.db.room.CartProductModel
import java.io.IOException

object Utils {

    //get Json String from assets with the fileName provided
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    //creates an OrderModel object
    fun createOrder(
        cart: ArrayList<CartProductModel>,
        id: String,
        name: String,
        email: String,
        totalPrice:String
    ): OrderModel {
        return OrderModel(
            id = id,
            name = name,
            email = email,
            products = cart,
            total_price = totalPrice
        )
    }

    //returns total price of products in the cart
    fun cartTotalPrice(cart:ArrayList<CartProductModel>):Double{
        var totalPrice = 0.00
        for(cartItem in cart){
            totalPrice += cartItem.price.toDouble()
        }
        return totalPrice
    }
}