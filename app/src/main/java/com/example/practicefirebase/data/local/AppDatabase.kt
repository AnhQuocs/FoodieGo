package com.example.practicefirebase.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practicefirebase.data.local.order.OrderDao
import com.example.practicefirebase.data.local.order.OrderItemDao
import com.example.practicefirebase.data.local.order.OrderWithItemsDao
import com.example.practicefirebase.domain.cart.CartModel
import com.example.practicefirebase.domain.order.OrderItemModel
import com.example.practicefirebase.domain.order.OrderModel
import com.example.practicefirebase.domain.other.Converters

@Database(entities = [CartModel::class, OrderModel::class, OrderItemModel::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun orderWithItemsDao(): OrderWithItemsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "foodieGo_db"
                )

                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}