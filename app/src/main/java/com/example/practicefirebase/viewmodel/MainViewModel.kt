package com.example.practicefirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.domain.product.ProductModel
import com.example.practicefirebase.domain.RestaurantModel
import com.example.practicefirebase.repository.MainRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadCake(): LiveData<MutableList<ProductModel>> {
        return repository.loadCake()
    }

    fun loadProductByCategoryId(categoryId: String): LiveData<MutableList<ProductModel>> {
        return repository.loadProductByCategoryId(categoryId)
    }

    fun loadProductById(id: Int): LiveData<MutableList<ProductModel>> {
        return repository.loadProductById(id)
    }

    fun loadRestaurant(): LiveData<MutableList<RestaurantModel>> {
        return repository.loadRestaurant()
    }
}