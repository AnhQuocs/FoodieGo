package com.example.practicefirebase.domain.product

import java.io.Serializable

data class ProductModel(
    var Id: Int = 0,
    var CategoryId: String? = null,
    var ImagePath: String = "",
    var Name: String = "",
    var Description: String = "",
    var Price: String = ""
): Serializable