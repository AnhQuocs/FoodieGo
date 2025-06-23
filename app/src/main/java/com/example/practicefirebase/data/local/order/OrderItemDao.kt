package com.example.practicefirebase.data.local.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicefirebase.domain.order.OrderItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemModel>)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getItemsForOrder(orderId: String): Flow<List<OrderItemModel>>
}