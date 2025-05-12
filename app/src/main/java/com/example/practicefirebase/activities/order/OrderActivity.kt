package com.example.practicefirebase.activities.order

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.checkout.CheckoutActivity
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.ProductModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class OrderActivity : AppCompatActivity() {
    private lateinit var product: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)

        setContent {
            product = intent.getSerializableExtra("product") as ProductModel
            Order(product = product, onBackClick = {finish()})
        }
    }
}

@Composable
fun Order(
    product: ProductModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var showItemLoading by remember { mutableStateOf(true) }

    var productQuantity by remember { mutableStateOf(1) }
    var tableQuantity by remember { mutableStateOf(1) }

    val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE) // Format as yyyy-MM-dd
    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) // Format as HH:mm
    var selectedDate by remember { mutableStateOf(currentDate) }
    var selectedTime by remember { mutableStateOf(currentTime) }

    var selectedPayment by remember { mutableStateOf("Visa *1234") }

    LaunchedEffect(Unit) {
        showItemLoading = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item {
            TopBar(
                onBackClick = { onBackClick() },
                modifier = Modifier.background(color = Color.White).height(75.dp),
                title = "Order",
                color = Color.Black
            )
        }

        item { InfoItemSection(product, showItemLoading) }
        item {
            BookingDetailsSection(
                product = product,
                productQuantity = productQuantity,
                onProductQuantityChange = {productQuantity = it},
                tableQuantity = tableQuantity,
                onTableQuantityChange = {tableQuantity = it},
                selectedDate = selectedDate,
                onDateSelected = {selectedDate = it},
                selectedTime = selectedTime,
                onTimeSelected = {selectedTime = it},
                selectedPayment = selectedPayment,
                onPaymentSelected = {selectedPayment = it},
                onOrderClick = {
                    val intent = Intent(context, CheckoutActivity::class.java).apply {
                        putExtra("product", product)
                        putExtra("productQuantity", productQuantity)
                        putExtra("tableQuantity", tableQuantity)
                        putExtra("date", selectedDate)
                        putExtra("time", selectedTime)
                        putExtra("payment", selectedPayment)
                    }
                    startActivity(context, intent, null)
                }
            )
        }
    }
}