package com.example.practicefirebase.viewmodel

import androidx.lifecycle.LiveData
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.repository.DashboardRepository

class DashboardViewModel {
    private val repository = DashboardRepository()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadCake(): LiveData<MutableList<ProductModel>> {
        return repository.loadCake()
    }

    fun loadProductById(categoryId: String): LiveData<MutableList<ProductModel>> {
        return repository.loadProductById(categoryId)
    }
}