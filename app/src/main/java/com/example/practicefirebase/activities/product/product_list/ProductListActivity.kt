package com.example.practicefirebase.activities.product.product_list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R
import com.example.practicefirebase.domain.product.ProductModel
import com.example.practicefirebase.domain.RestaurantModel
import com.example.practicefirebase.viewmodel.MainViewModel

class ProductActivity : AppCompatActivity() {
    private var categoryId: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        categoryId = intent.getStringExtra("categoryId") ?: ""

        setContent {
            ProductList(categoryId, onBackClick = { finish() })
        }
    }
}

@Composable
fun ProductList(
    categoryId: String,
    onBackClick: () -> Unit
) {
    val viewmodel = MainViewModel()

    val products = remember { mutableStateListOf<ProductModel>() }
    val nearest = remember { mutableStateListOf<RestaurantModel>() }

    var showProductListLoading by remember { mutableStateOf(true) }
    var showNearestLoading by remember { mutableStateOf(true) }

    LaunchedEffect(categoryId) {
        viewmodel.loadProductByCategoryId(categoryId).observeForever {
            products.clear()
            products.addAll(it)
            showProductListLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewmodel.loadRestaurant().observeForever {
            nearest.clear()
            nearest.addAll(it)
            showNearestLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFF9ED))
    ) {
        item {
            TopBar(
                onBackClick = onBackClick,
                modifier = Modifier
                    .height(120.dp)
                    .background(color = colorResource(R.color.blue)),
                title = "Choose a Dish",
                color = Color.White
            )
        }
        item {
            BannerInList()
        }

        item {
            Text(
                "Product List",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 6.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            ProductListSection(
                products = products,
                showProductListLoading = showProductListLoading
            )
        }

        item { NearestListSection(list = nearest, showNearestLoading = showNearestLoading) }
    }
}