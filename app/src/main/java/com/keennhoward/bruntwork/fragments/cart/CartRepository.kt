package com.keennhoward.bruntwork.fragments.cart

import com.keennhoward.bruntwork.db.room.CartDAO
import com.keennhoward.bruntwork.db.room.CartProductModel

class CartRepository(private val dao: CartDAO) {

    val cart = dao.getCart()

    suspend fun delete(cartProductModel: CartProductModel){
        dao.delete(cartProductModel)
    }
}