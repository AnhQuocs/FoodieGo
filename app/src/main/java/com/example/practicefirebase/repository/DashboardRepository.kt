package com.example.practicefirebase.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.domain.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for(childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    item?.let {
                        list.add(it)
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

    fun loadCake(): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("Product")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()

                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ProductModel::class.java)
                    if (item?.CategoryId == "0") {
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

//    fun loadProductById(): LiveData<MutableList<ProductModel>> {
//        val listData = MutableLiveData<MutableList<ProductModel>>()
//        val ref = firebaseDatabase.getReference("")
//    }
}