package com.example.practicefirebase.domain.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "order_items")
data class OrderItemModel(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val orderId: String,
    val productId: Int,
    val title: String,
    val description: String,
    val price: Float,
    val quantity: Int,
    val image: String
)