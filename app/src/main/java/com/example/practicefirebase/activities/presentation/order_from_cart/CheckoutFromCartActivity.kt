package com.example.practicefirebase.activities.presentation.order_from_cart

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.AsyncImage
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.presentation.checkout.CheckoutItemRow
import com.example.practicefirebase.activities.presentation.order.ButtonSection
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.CartModel
import java.text.NumberFormat
import java.util.Locale

class CheckoutFromCartActivity : AppCompatActivity() {

    private var tableQuantity: Int = 0
    private var date: String = ""
    private var time: String = ""
    private var payment: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout_from_cart2)

        setContent {
            val cartItems = intent.getSerializableExtra("productList") as? ArrayList<CartModel> ?: arrayListOf()
            val productQuantities = intent.getSerializableExtra("productQuantities") as? HashMap<Int, Int> ?: hashMapOf()

            tableQuantity = intent.getIntExtra("tableQuantity", 0)
            date = intent.getStringExtra("date") ?: ""
            time = intent.getStringExtra("time") ?: ""
            payment = intent.getStringExtra("payment") ?: ""

            CheckoutFromCartScreen(
                cartItems = cartItems,
                productQuantity = productQuantities,
                onBackClick = { finish() },
                tableQuantity = tableQuantity,
                date = date,
                time = time,
                payment = payment
            )
        }
    }
}

@Composable
fun CheckoutFromCartScreen(
    cartItems: List<CartModel>,
    productQuantity: Map<Int, Int>,
    onBackClick: () -> Unit,
    tableQuantity: Int,
    date: String,
    time: String,
    payment: String,
) {
    Scaffold(
        topBar = {
            TopBar(
                onBackClick = { onBackClick() },
                modifier = Modifier
                    .background(color = Color.White)
                    .height(75.dp),
                title = "Checkout",
                color = Color.Black
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                LineGrey()
                CheckoutItemRow(title = "PAYMENT", value = payment, color = Color.Black)
                LineGrey()
                CheckoutItemRow(title = "PROMOS", value = "Apply promo code", color = Color.Black.copy(alpha = 0.5f), hasArrow = true)
                LineGrey()
                CheckoutItemRow(title = "DATE", value = date, color = Color.Black)
                LineGrey()
                CheckoutItemRow(title = "TIME", value = time, color = Color.Black)
                LineGrey()
            }

            item {
                ProductItemFromCart(
                    cartItems = cartItems,
                    productQuantity = productQuantity
                )
            }

            item {
                LineGrey()
                ButtonSection(title = "Place order", onOrderClick = {})
            }
        }
    }
}

@Composable
fun ProductItemFromCart(
    cartItems: List<CartModel>,
    productQuantity: Map<Int, Int>
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "ITEMS",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .width(80.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "DESCRIPTION",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            )
            Text(
                "TOTAL",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        cartItems.forEach { item ->
            val quantity = productQuantity[item.productId] ?: 1
            val totalPrice = item.price.replace("$", "").replace(",", ".").toDoubleOrNull() ?: 0.0
            val total = totalPrice * quantity
            val totalFormatted = NumberFormat
                .getCurrencyInstance(Locale.US)
                .format(total)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Quantity: $quantity",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Price: ${item.price}"
                    )
                }

                Text(
                    text = "${totalFormatted}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}