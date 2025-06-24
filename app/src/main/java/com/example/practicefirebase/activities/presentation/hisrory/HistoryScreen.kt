package com.example.practicefirebase.activities.presentation.hisrory

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryScreen(
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val ordersWithItems by orderViewModel.ordersWithItems.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        orderViewModel.loadAllOrdersWithItems()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Order\nHistory\uD83E\uDDFE",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 24.dp, bottom = 8.dp)
        )

        if (ordersWithItems.isEmpty()) {
            Text(
                "No orders yet!",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(ordersWithItems) { orderWithItems ->

                    val time = orderWithItems.order.createAt
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val formattedTime = time.format(formatter)

                    val price = orderWithItems.order.totalPrice
                    val taxes = price * 0.08
                    val serviceFee = 0.99 * orderWithItems.order.tableQuantity
                    val total = price + taxes + serviceFee
                    val roundedTotal = String.format(Locale.US, "%.2f", total)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Order Successfully",
                                    fontSize = 16.sp,
                                    color = Color(0xFF007BFF)
                                )

                                Text(
                                    text = "$formattedTime",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }


                            Spacer(modifier = Modifier.height(16.dp))

                            orderWithItems.items.forEach { item ->

                                val originPrice = item.price + (item.price * 0.2)
                                val roundedOriginTotal = String.format(Locale.US, "%.2f", originPrice)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    AsyncImage(
                                        model = item.image,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth(0.2f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .aspectRatio(1f),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = item.title,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 15.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "$$roundedOriginTotal",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                textDecoration = TextDecoration.LineThrough
                                            )

                                            Spacer(modifier = Modifier.width(4.dp))

                                            Text(
                                                text = "$${item.price}",
                                                fontSize = 14.sp,
                                                color = Color.Black
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            Text(
                                                text = "x${item.quantity}",
                                                color = Color.Gray,
                                                modifier = Modifier.padding(end = 16.dp)
                                            )
                                        }
                                    }
                                }

                                LineGrey()
                            }

                            val productText = if (orderWithItems.items.size == 1) {
                                "product"
                            } else {
                                "products"
                            }

                            Text(
                                text = buildAnnotatedString {
                                    append("Total amount (${orderWithItems.items.size} $productText): ")

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("$$roundedTotal")
                                    }
                                },
                                textAlign = TextAlign.End,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(end = 8.dp)
                                    .align(Alignment.End)
                                    .height(40.dp)
                                    .background(Color.White)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .clickable {
                                        val intent = Intent(context, HistoryDetailActivity::class.java)
                                            .putExtra("orderId", orderWithItems.order.id)
                                        startActivity(context, intent, null)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "View details",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}