package com.example.practicefirebase.activities.presentation.order_from_cart

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.presentation.order.ButtonSection
import com.example.practicefirebase.activities.presentation.order.DatePickerItem
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.activities.presentation.order.Payment
import com.example.practicefirebase.activities.presentation.order.QuantitySection
import com.example.practicefirebase.activities.presentation.order.TimePickerItem
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.cart.CartModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class OrderFromCartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_from_cart)

        setContent {
            val cartItems = intent.getSerializableExtra("productList") as? ArrayList<CartModel> ?: arrayListOf()
            OrderFromCartScreen(
                cartItems = cartItems,
                onBackClick = { finish() }
            )
        }
    }
}

@Composable
fun OrderFromCartScreen(
    cartItems: List<CartModel>,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var productQuantities by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var tableQuantity by remember { mutableStateOf(1) }
    var selectedPayment by remember { mutableStateOf("Visa *1234") }

    val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE) // Format as yyyy-MM-dd
    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) // Format as HH:mm
    var selectedDate by remember { mutableStateOf(currentDate) }
    var selectedTime by remember { mutableStateOf(currentTime) }

    var showItemLoading by remember { mutableStateOf(true) }

    val totalPrice = cartItems.sumOf { item ->
        val quantity = productQuantities[item.productId] ?: 1
        val price = item.price
            .replace("$", "")
            .replace(",", ".")
            .toDoubleOrNull() ?: 0.0
        price * quantity
    }.toFloat()

    LaunchedEffect(Unit) {
        showItemLoading = false
    }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = { onBackClick() },
                modifier = Modifier
                    .background(color = Color.White)
                    .height(75.dp),
                title = "Order",
                color = Color.Black
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            cartItems.forEach { item ->
                val quantity = productQuantities[item.productId] ?: 1
                item {
                    InfoItemFromCart(
                        cartItem = item,
                        productQuantity = quantity,
                        showItemLoading = showItemLoading,
                        onQuantityChange = { newQuantity ->
                            productQuantities = productQuantities.toMutableMap().apply {
                                put(item.productId, newQuantity)
                            }
                        }
                    )
                }
            }

            item {
                LineGrey()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DatePickerItem(label = "Booking Date", selectedDate = selectedDate, onDateSelected = { selectedDate = it })
                    TimePickerItem(label = "Booking Time", selectedTime = selectedTime, onTimeSelected = { selectedTime = it })
                }
                LineGrey()
                QuantitySection(title = "Number of Tables", quantity = tableQuantity, onQuantityChange = { tableQuantity = it })
                LineGrey()
                Payment(selectedPayment = selectedPayment, onPaymentSelected = { selectedPayment = it })
                LineGrey()
                TotalPriceFromCart(
                    totalPrice = totalPrice,
                    quantity = cartItems.size,
                    tableQuantity = tableQuantity
                )
                LineGrey()
                ButtonSection(
                    title = "Order",
                    onClick = {
                        val quantitiesMap = HashMap(productQuantities)
                        val intent = Intent(context, CheckoutFromCartActivity::class.java).apply {
                            putExtra("productList", ArrayList(cartItems))
                            putExtra("productQuantities", quantitiesMap)
                            putExtra("tableQuantity", tableQuantity)
                            putExtra("date", selectedDate)
                            putExtra("time", selectedTime)
                            putExtra("payment", selectedPayment)
                            putExtra("totalPrice", totalPrice)
                        }
                        startActivity(context, intent, null)
                    }
                )
            }
        }
    }
}