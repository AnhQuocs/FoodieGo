package com.example.practicefirebase.data.local.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicefirebase.domain.order.OrderModel
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderModel)

    @Query("SELECT * FROM orders ORDER BY createAt DESC")
    fun getAllOrders(): Flow<List<OrderModel>>

    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: String): OrderModel?
}