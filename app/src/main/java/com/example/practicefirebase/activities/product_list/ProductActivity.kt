package com.example.practicefirebase.activities.product_list

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.viewmodel.DashboardViewModel

class ProductActivity : AppCompatActivity() {
    private var categoryId: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        categoryId = intent.getStringExtra("categoryId")?: ""

        setContent {
            ProductList(categoryId, onBackClick = {finish()})
        }
    }
}

@Composable
fun ProductList(
    categoryId: String,
    onBackClick: () -> Unit
) {
    val viewmodel = DashboardViewModel()

    val products = remember { mutableStateListOf<ProductModel>() }

    var showProductListLoading by remember { mutableStateOf(true) }

    LaunchedEffect(categoryId) {
        viewmodel.loadProductById(categoryId).observeForever {
            products.clear()
            products.addAll(it)
        }
    }

    LaunchedEffect(Unit) {
        viewmodel.loadProductById(categoryId).observeForever {
            products.clear()
            products.addAll(it)
            showProductListLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFF9ED))
    ) {
        item { TopBar(onBackClick = onBackClick) }
        item { ProductListSection(products = products, showProductListLoading = showProductListLoading) }
    }
}