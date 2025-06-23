package com.example.practicefirebase.activities.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.dashboard.Banner
import com.example.practicefirebase.activities.dashboard.SliderSection
import com.example.practicefirebase.activities.dashboard.TabSection
import com.example.practicefirebase.activities.dashboard.products.cakelist.CakeSection
import com.example.practicefirebase.activities.dashboard.products.categories.CategorySection
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.domain.product.ProductModel
import com.example.practicefirebase.search.Search
import com.example.practicefirebase.viewmodel.MainViewModel

@Composable
fun HomeScreen() {
    val viewmodel: MainViewModel = hiltViewModel()

    val category = remember { mutableStateListOf<CategoryModel>() }
    val cake = remember { mutableStateListOf<ProductModel>() }

    var showCategoryLoading by remember { mutableStateOf(true) }
    var showCakeLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewmodel.loadCategory().observeForever {
            category.clear()
            category.addAll(it)
            showCategoryLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewmodel.loadCake().observeForever {
            cake.clear()
            cake.addAll(it)
            showCakeLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        item { Search() }
        item { TabSection() }
        item { SliderSection() }
        item { CategorySection(category = category, showCategoryLoading = showCategoryLoading) }
        item { CakeSection(cake = cake, showCakeLoading = showCakeLoading) }
        item { Banner() }
    }
}