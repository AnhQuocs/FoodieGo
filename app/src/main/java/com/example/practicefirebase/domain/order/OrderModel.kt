package com.example.practicefirebase.domain.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "orders")
data class OrderModel(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val createAt: LocalDateTime = LocalDateTime.now(),
    val totalPrice: Float,
    val tableQuantity: Int,
    val mealDate: String,
    val mealTime: String
)