package com.example.practicefirebase.repository

import com.example.practicefirebase.data.local.CartDao
import com.example.practicefirebase.domain.cart.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val cartItems: Flow<List<CartModel>> = cartDao.getCartItems()

    suspend fun addToCart(cartItem: CartModel) {
        val isExist = cartDao.isProductInCart(cartItem.productId) > 0
        if(!isExist) {
            cartDao.addToCart(cartItem)
        }
    }

    suspend fun removeFromCart(productId: Int) {
        cartDao.removeFromCart(productId)
    }
}