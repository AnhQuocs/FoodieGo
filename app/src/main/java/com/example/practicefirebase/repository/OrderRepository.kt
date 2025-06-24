package com.example.practicefirebase.repository

import com.example.practicefirebase.data.local.order.OrderDao
import com.example.practicefirebase.data.local.order.OrderItemDao
import com.example.practicefirebase.data.local.order.OrderWithItemsDao
import com.example.practicefirebase.domain.order.OrderItemModel
import com.example.practicefirebase.domain.order.OrderModel
import com.example.practicefirebase.domain.order.OrderWithItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao,
    private val orderWithItemsDao: OrderWithItemsDao
) {

    suspend fun insertOrderWithItems(order: OrderModel, items: List<OrderItemModel>) {
        orderDao.insertOrder(order)
        orderItemDao.insertOrderItems(items)
    }

    fun getAllOrders(): Flow<List<OrderModel>> = orderDao.getAllOrders()

    fun getOrderWithItems(orderId: String): Flow<OrderWithItems> =
        orderWithItemsDao.getOrderWithItems(orderId)

    fun getAllOrdersWithItems(): Flow<List<OrderWithItems>> =
        orderWithItemsDao.getAllOrdersWithItems()
}