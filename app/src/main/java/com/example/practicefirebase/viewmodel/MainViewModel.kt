package com.example.practicefirebase.viewmodel

import androidx.lifecycle.LiveData
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.domain.RestaurantModel
import com.example.practicefirebase.repository.MainRepository

class MainViewModel {
    private val repository = MainRepository()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadCake(): LiveData<MutableList<ProductModel>> {
        return repository.loadCake()
    }

    fun loadProductById(categoryId: String): LiveData<MutableList<ProductModel>> {
        return repository.loadProductById(categoryId)
    }

    fun loadRestaurant(): LiveData<MutableList<RestaurantModel>> {
        return repository.loadRestaurant()
    }
}