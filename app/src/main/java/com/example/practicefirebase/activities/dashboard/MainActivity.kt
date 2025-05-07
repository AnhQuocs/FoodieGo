package com.example.practicefirebase.activities.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.dashboard.products.cakelist.CakeSection
import com.example.practicefirebase.activities.dashboard.products.categories.CategorySection
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.domain.CategoryModel
import com.example.practicefirebase.search.Search
import com.example.practicefirebase.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardScreen()
        }
    }
}

@Composable
@Preview
fun DashboardScreen() {

    val viewmodel = MainViewModel()

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

    Scaffold(
        bottomBar = { MyBottomBar() }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.white))
                .padding(paddingValues)
        ) {
            item { Search() }
            item { TabSection() }
            item { SliderSection() }
            item { CategorySection(category = category, showCategoryLoading = showCategoryLoading) }
            item { CakeSection(cake = cake, showCakeLoading = showCakeLoading) }
            item { Banner() }
        }
    }
}