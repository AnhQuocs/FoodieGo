package com.example.practicefirebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.practicefirebase.domain.cart.CartModel
import com.example.practicefirebase.domain.product.ProductModel
import com.example.practicefirebase.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    val cartItems = cartRepository.cartItems.asLiveData()

    private val _cartEvent = MutableSharedFlow<String>()
    val cartEvent = _cartEvent.asSharedFlow()

    fun addToCart(product: ProductModel) {
        viewModelScope.launch {
            val cartItem = CartModel(
                productId = product.Id,
                title = product.Name,
                description = product.Description,
                price = product.Price,
                image = product.ImagePath
            )

            cartRepository.addToCart(cartItem)

            _cartEvent.emit("Add to cart successfully!\uD83D\uDED2")
        }
    }

    fun removeFromCart(cartItem: CartModel) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId = cartItem.productId)
        }
    }
}