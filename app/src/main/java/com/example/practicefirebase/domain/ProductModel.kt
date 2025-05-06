package com.example.practicefirebase.domain

data class ProductModel(
    var Id: Int = 0,
    var CategoryId: String? = null,
    var ImagePath: String = "",
    var Name: String = "",
    var Price: String = "",
    var Quantity: Int = 0
)