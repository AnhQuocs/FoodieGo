package com.example.practicefirebase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practicefirebase.domain.CakeModel
import com.example.practicefirebase.domain.CategoryModel
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

    fun loadCake(): LiveData<MutableList<CakeModel>> {
        val listData = MutableLiveData<MutableList<CakeModel>>()
        val ref = firebaseDatabase.getReference("Cake Products")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CakeModel>()
                for(childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CakeModel::class.java)
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
}