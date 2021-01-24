package com.keennhoward.bruntwork.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class CartProductModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name ="product_id")
    var product_id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "price")
    var price: String,

    @ColumnInfo(name = "bg_color")
    var bgColor: String
)