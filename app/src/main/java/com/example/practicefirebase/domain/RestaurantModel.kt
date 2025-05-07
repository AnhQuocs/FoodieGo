package com.example.practicefirebase.domain

import java.io.Serializable

data class RestaurantModel(
    var Id: Int = 0,
    var Title: String = "",
    var Latitude: Double = 0.0,
    var Longtitude: Double = 0.0,
    val Call: String = "",
    var Address: String = "",
    var Activity: String = "",
    var ShortAddress: String = "",
    var Hours: String = "",
    var ImagePath: String = ""
): Serializable