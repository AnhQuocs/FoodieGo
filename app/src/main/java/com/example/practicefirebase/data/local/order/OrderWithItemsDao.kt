package com.example.practicefirebase.data.local.order

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.practicefirebase.domain.order.OrderWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderWithItemsDao {
    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    fun getOrderWithItems(orderId: String): Flow<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM orders ORDER BY createAt DESC")
    fun getAllOrdersWithItems(): Flow<List<OrderWithItems>>
}