package com.example.practicefirebase.domain.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cart_items")
data class CartModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String
) : Serializable