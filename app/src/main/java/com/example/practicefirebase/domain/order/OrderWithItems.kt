package com.example.practicefirebase.domain.order

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithItems(
    @Embedded val order: OrderModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemModel>
)