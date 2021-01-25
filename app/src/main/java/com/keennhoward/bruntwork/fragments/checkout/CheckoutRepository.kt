package com.keennhoward.bruntwork.fragments.checkout

import com.keennhoward.bruntwork.db.room.CartDAO

class CheckoutRepository(private val dao: CartDAO){
    val cart = dao.getCart()

    suspend fun clearCart(){
        dao.clearCart()
    }

}