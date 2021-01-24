package com.keennhoward.bruntwork.checkout

import com.keennhoward.bruntwork.db.room.CartProductModel

data class OrderModel(
    var id:String,
    var name:String,
    var email:String,
    var total_price: String,
    var products:List<CartProductModel>
)