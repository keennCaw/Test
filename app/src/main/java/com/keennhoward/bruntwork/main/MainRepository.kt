package com.keennhoward.bruntwork.main

import com.keennhoward.bruntwork.db.room.CartDAO

class MainRepository(private val dao: CartDAO) {

    val cart = dao.getCart()

}