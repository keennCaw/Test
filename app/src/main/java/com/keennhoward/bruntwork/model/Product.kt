package com.keennhoward.bruntwork.model

data class Product(
        var products: List<ProductData>
){
    data class ProductData(
            var id:String,
            var name:String,
            var category: String,
            var price: String,
            var bgColor:String
    )
}
