package com.example.practicefirebase.activities.checkout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.ProductModel

class CheckoutActivity : AppCompatActivity() {
    private lateinit var product: ProductModel
    private var productQuantity: Int = 0
    private var tableQuantity: Int = 0
    private var date: String = ""
    private var time: String = ""
    private var payment: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)

        setContent {
            product = intent.getSerializableExtra("product") as ProductModel
            productQuantity = intent.getIntExtra("productQuantity", 0)
            tableQuantity = intent.getIntExtra("tableQuantity", 0)
            date = intent.getStringExtra("date") ?: ""
            time = intent.getStringExtra("time") ?: ""
            payment = intent.getStringExtra("payment") ?: ""

            Checkout(
                product = product,
                productQuantity = productQuantity,
                tableQuantity = tableQuantity,
                date = date,
                time = time,
                payment = payment,
                onBackClick = {finish()}
            )
        }
    }
}

@Composable
fun Checkout(
    product: ProductModel,
    productQuantity: Int,
    tableQuantity: Int,
    date: String,
    time: String,
    payment: String,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            TopBar(
                title = "Checkout",
                color = Color.Black,
                onBackClick = { onBackClick() },
                modifier = Modifier.background(Color.White)
            )
        }

        item {
            CheckoutInfoSection(
                product = product,
                payment = payment,
                productQuantity = productQuantity,
                tableQuantity = tableQuantity,
                date = date,
                time = time,
                onOrderClick = {}
            )
        }
    }
}