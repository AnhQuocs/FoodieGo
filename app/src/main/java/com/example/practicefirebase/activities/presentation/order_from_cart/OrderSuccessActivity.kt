package com.example.practicefirebase.activities.presentation.order_from_cart

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.dashboard.MainActivity
import com.example.practicefirebase.activities.presentation.order.LineGrey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrderSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productSize = intent.getIntExtra("productSize", 0)
        val tableQuantity = intent.getIntExtra("tableQuantity", 0)
        val totalPrice = intent.getFloatExtra("totalPrice", 0.0f)
        val date = intent.getStringExtra("date") ?: ""
        val time = intent.getStringExtra("time") ?: ""

        setContent {
            OrderSuccessScreen(
                productSize = productSize,
                tableQuantity = tableQuantity,
                totalPrice = totalPrice,
                date = date,
                time = time,
                onBackClick = { finish() },
                onCartClick = {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("navigateTo", "cart")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                },
                onHomeClick = {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("navigateTo", "home")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                }
            )
        }
    }
}

@Composable
fun OrderSuccessScreen(
    productSize: Int,
    tableQuantity: Int,
    totalPrice: Float,
    date: String,
    time: String,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val timeNow = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = timeNow.format(formatter)

    val taxes = totalPrice * 0.08
    val serviceFee = 0.99 * tableQuantity
    val total = totalPrice + taxes + serviceFee
    val roundedTotal = String.format(Locale.US, "%.2f", total)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .background(Color(0xFF433D93))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = { onCartClick() }
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(color = Color(0xFFD09D5A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "Order\nSuccessfully",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = { onHomeClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF433D93).copy(alpha = 0.9f)
                    )
                ) {
                    Text(
                        text = "Home",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Order Time",
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = formattedTime.toString()
                    )
                }

                LineGrey(modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Products",
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "$productSize"
                    )
                }

                LineGrey(modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total Price",
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "$$roundedTotal"
                    )
                }

                LineGrey(modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Meal Date",
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = date
                    )
                }

                LineGrey(modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Meal Time",
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = time
                    )
                }
            }
        }
    }
}