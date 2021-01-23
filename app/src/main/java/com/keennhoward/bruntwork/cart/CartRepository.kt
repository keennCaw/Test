package com.keennhoward.bruntwork.cart

import com.keennhoward.bruntwork.room.CartDAO
import com.keennhoward.bruntwork.room.CartProductModel

class CartRepository(private val dao: CartDAO) {

    val cart = dao.getCart()

    suspend fun delete(cartProductModel: CartProductModel){
        dao.delete(cartProductModel)
    }
}