package com.example.practicefirebase.activities.presentation.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.presentation.order_from_cart.OrderSuccessActivity
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.order.OrderItemModel
import com.example.practicefirebase.domain.order.OrderModel
import com.example.practicefirebase.domain.product.ProductModel
import com.example.practicefirebase.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    val orderViewModel: OrderViewModel = hiltViewModel()

    val context = LocalContext.current
    val activity = context as? Activity

    val totalPrice = product.Price
        .replace("$", "")
        .replace(",", ".")
        .toFloatOrNull() ?: 0.0f

    Log.d("ProductPrice", "$totalPrice")

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
                modifier = Modifier.background(Color.White).height(100.dp)
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
                onOrderClick = {
                    val order = OrderModel(
                        totalPrice = totalPrice,
                        tableQuantity = tableQuantity,
                        mealTime = time,
                        mealDate = date
                    )

                    val orderItems = OrderItemModel(
                            orderId = order.id,
                            productId = product.Id,
                            title = product.Name,
                            description = product.Description,
                            price = product.Price.replace("$", "").replace(",", ".").toFloatOrNull() ?: 0f,
                            quantity = 1,
                            image = product.ImagePath
                    )

                    orderViewModel.insertOrderWithItems(order, listOf(orderItems))

                    val intent = Intent(context, OrderSuccessActivity::class.java)
                        .putExtra("productSize", 1)
                        .putExtra("totalPrice", totalPrice)
                        .putExtra("date", date)
                        .putExtra("time", time)
                        .putExtra("tableQuantity", tableQuantity)

                    context.startActivity(intent)
                    activity?.finish()
                }
            )
        }
    }
}