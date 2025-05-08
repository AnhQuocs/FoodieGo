package com.example.practicefirebase.activities.product.product_detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicefirebase.R
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.viewmodel.MainViewModel

class ProductDetailActivity : AppCompatActivity() {
    private var productId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        productId = intent.getIntExtra("productId", 0)

        setContent {
            ProductDetail(productId = productId, onBackClick = {finish()})
        }
    }
}

@Composable
fun ProductDetail(
    productId: Int,
    onBackClick: () -> Unit
) {
    val viewModel = MainViewModel()

    val products = remember { mutableStateListOf<ProductModel>() }

    var showProductDetailLoading by remember { mutableStateOf(true) }

    LaunchedEffect(productId) {
        viewModel.loadProductById(productId).observeForever {
            products.clear()
            products.addAll(it)
        }
    }

    LaunchedEffect(Unit) {
        showProductDetailLoading = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            if(products.isNotEmpty()) {
                ProductDetailSection(
                    products = products.first(),
                    showProductDetailLoading = showProductDetailLoading,
                    onBackClick = onBackClick
                )
            }
        }
    }
}