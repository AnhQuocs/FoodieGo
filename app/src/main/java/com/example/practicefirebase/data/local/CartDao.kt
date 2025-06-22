package com.example.practicefirebase.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicefirebase.domain.CartModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartModel: CartModel)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun removeFromCart(productId: Int)

    @Query("SELECT COUNT(*) FROM cart_items WHERE productId = :productId")
    suspend fun isProductInCart(productId: Int): Int
}