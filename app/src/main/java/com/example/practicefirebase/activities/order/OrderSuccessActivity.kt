package com.example.practicefirebase.activities.order

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.product.product_list.ProductListSection
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.viewmodel.MainViewModel

class OrderSuccessActivity : AppCompatActivity() {
    private var categoryId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_success)

        categoryId = intent.getStringExtra("categoryId") ?: ""

        setContent {
            OrderSuccess(categoryId)
        }
    }
}

@Composable
fun OrderSuccess(
    categoryId: String
) {
    val viewmodel = MainViewModel()

    val products = remember { mutableStateListOf<ProductModel>() }

    var showProductListLoading by remember { mutableStateOf(true) }

    LaunchedEffect(categoryId) {
        viewmodel.loadProductByCategoryId(categoryId).observeForever {
            products.clear()
            products.addAll(it)
            showProductListLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(36.dp))
            OrderSuccessText()
            LineGrey()
        }

        item {
            ProductListSection(
                products = products,
                showProductListLoading = showProductListLoading
            )
        }
    }
}

@Preview
@Composable
fun OrderSuccessText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CheckCircleOutline,
                contentDescription = null,
                tint = Color(0xFF33CC66),
                modifier = Modifier.size(32.dp)
            )
            Text(
                "Order Successfully",
                fontSize = 20.sp,
                color = Color(0xFF33CC66),
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Remember to be on time to enjoy the delicious dishes!",
            fontSize = 15.sp,
            color = Color.Black
        )
    }
}