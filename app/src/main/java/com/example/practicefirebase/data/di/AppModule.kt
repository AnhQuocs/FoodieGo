package com.example.practicefirebase.data.di

import android.content.Context
import com.example.practicefirebase.data.local.AppDatabase
import com.example.practicefirebase.data.local.CartDao
import com.example.practicefirebase.data.local.order.OrderDao
import com.example.practicefirebase.data.local.order.OrderItemDao
import com.example.practicefirebase.data.local.order.OrderWithItemsDao
import com.example.practicefirebase.repository.CartRepository
import com.example.practicefirebase.repository.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao = database.cartDao()

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository =
        CartRepository(cartDao)

    @Provides
    fun provideOrderDao(db: AppDatabase): OrderDao = db.orderDao()

    @Provides
    fun provideOrderItemDao(db: AppDatabase): OrderItemDao = db.orderItemDao()

    @Provides
    fun provideOrderWithItemsDao(db: AppDatabase): OrderWithItemsDao = db.orderWithItemsDao()

    @Provides
    fun provideOrderRepository(
        orderDao: OrderDao,
        orderItemDao: OrderItemDao,
        orderWithItemsDao: OrderWithItemsDao
    ): OrderRepository {
        return OrderRepository(orderDao, orderItemDao, orderWithItemsDao)
    }
}