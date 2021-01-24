package com.keennhoward.bruntwork.checkout

import com.keennhoward.bruntwork.db.room.CartDAO

class CheckoutRepository(private val dao: CartDAO){
    val cart = dao.getCart()

    suspend fun clearCart(){
        dao.clearCart()
    }

}