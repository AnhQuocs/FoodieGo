package com.example.practicefirebase.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.domain.product.ProductModel
import com.example.practicefirebase.domain.RestaurantModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = FirebaseDatabase.getInstance().getReference("Category")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    if (item != null) {
                        list.add(item)
                    }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Optional: log error
            }
        })

        return listData
    }

    fun loadCake(): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("Product")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()

                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ProductModel::class.java)
                    if (item?.CategoryId == "4") {
                        list.add(item)
                        Log.d("FirebaseDebug", "Item: ${item?.Name}")
                    }
                }

                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return listData
    }

    fun loadProductByCategoryId(categoryId: String): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("Product")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()

                for(childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ProductModel::class.java)
                    if(item?.CategoryId == categoryId) {
                        list.add(item)
                    }
                }

                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return listData
    }

    fun loadProductById(id: Int): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("Product")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()

                for(childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ProductModel::class.java)
                    if(item?.Id == id) {
                        list.add(item)
                    }
                }

                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return listData
    }

    fun loadRestaurant(): LiveData<MutableList<RestaurantModel>> {
        val listData = MutableLiveData<MutableList<RestaurantModel>>()
        val ref = firebaseDatabase.getReference("Restaurant")

        ref .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<RestaurantModel>()
                for(childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(RestaurantModel::class.java)
                    if(list != null) {
                        lists.add(list)
                    }
                }

                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return listData
    }
}