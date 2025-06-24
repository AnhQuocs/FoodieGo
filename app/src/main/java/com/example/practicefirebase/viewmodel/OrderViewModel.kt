package com.example.practicefirebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicefirebase.domain.order.OrderItemModel
import com.example.practicefirebase.domain.order.OrderModel
import com.example.practicefirebase.domain.order.OrderWithItems
import com.example.practicefirebase.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderModel>>(emptyList())
    val orders: StateFlow<List<OrderModel>> = _orders.asStateFlow()

    private val _selectedOrder = MutableStateFlow<OrderWithItems?>(null)
    val selectedOrder: StateFlow<OrderWithItems?> = _selectedOrder.asStateFlow()

    private val _ordersWithItems = MutableStateFlow<List<OrderWithItems>>(emptyList())
    val ordersWithItems: StateFlow<List<OrderWithItems>> = _ordersWithItems.asStateFlow()

    fun insertOrderWithItems(order: OrderModel, items: List<OrderItemModel>) {
        viewModelScope.launch {
            repository.insertOrderWithItems(order, items)
        }
    }

    fun loadAllOrders() {
        viewModelScope.launch {
            repository.getAllOrders().collect {
                _orders.value = it
            }
        }
    }

    fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            repository.getOrderWithItems(orderId).collect {
                _selectedOrder.value = it
            }
        }
    }

    fun loadAllOrdersWithItems() {
        viewModelScope.launch {
            repository.getAllOrdersWithItems().collect {
                _ordersWithItems.value = it
            }
        }
    }
}