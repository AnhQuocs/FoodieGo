package com.example.practicefirebase.activities.presentation.hisrory

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MarkUnreadChatAlt
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.TableBar
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.practicefirebase.activities.dashboard.MainActivity
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.order.OrderItemModel
import com.example.practicefirebase.domain.order.OrderModel
import com.example.practicefirebase.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class HistoryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val orderId = intent.getStringExtra("orderId") ?: ""

        setContent {
            HistoryDetailScreen(
                orderId = orderId,
                onBackClick = { finish() },
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
fun HistoryDetailScreen(
    orderId: String,
    orderViewModel: OrderViewModel = hiltViewModel(),
    onHomeClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val orderWithItems by orderViewModel.selectedOrder.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(orderId) {
        orderViewModel.loadOrderDetails(orderId)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(8.dp)
            ) { data ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    containerColor = Color(0xFF1E9584),
                    contentColor = Color.White,
                    action = {
                        TextButton(
                            onClick = { data.dismiss() }
                        ) {
                            Text(
                                text = data.visuals.actionLabel ?: "OK",
                                color = Color.Yellow
                            )
                        }
                    }
                ) {
                    Text(
                        text = data.visuals.message,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        },
        topBar = {
            TopBar(
                title = "Order Information",
                onBackClick = onBackClick,
                color = Color.Black,
                modifier = Modifier.height(75.dp)
            )
        },
        bottomBar = {
            BottomOrderBar(
                onHomeClick = onHomeClick,
                onAssess = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Growing features",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                orderWithItems?.let {
                    OrderInfoCard(
                        order = it.order,
                        items = it.items
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                orderWithItems?.let {
                    OrderItemCart(
                        order = it.order,
                        items = it.items
                    )
                }
            }

            item {
                orderWithItems?.let {
                    OrderCodeCard(
                        order = it.order
                    )
                }
            }
        }
    }
}

@Composable
fun OrderInfoCard(
    items: List<OrderItemModel>,
    order: OrderModel
) {
    val fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val formattedTime = order.createAt.format(fullFormatter)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF1E9584))
        ) {
            Text(
                "Placed order",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CheckCircleOutline,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .size(16.dp)
                    .align(Alignment.Top)
            )

            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    "Order placed successfully",
                    lineHeight = 18.sp,
                    fontSize = 14.sp,
                    color = Color(0xFF1E9584)
                )

                Text(
                    text = "$formattedTime",
                    lineHeight = 18.sp,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        LineGrey()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                "Booking detail",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.DateRange,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .size(18.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                ) {
                    Text("Meal date: ${order.mealDate}", fontSize = 14.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Timer,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .size(18.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                ) {
                    Text("Meal time: ${order.mealTime}", fontSize = 14.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Restaurant,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .size(18.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                ) {
                    Text("Products: ${items.size}", fontSize = 14.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.TableBar,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .size(18.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                ) {
                    Text("Number of tables: ${order.tableQuantity}", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun OrderItemCart(
    items: List<OrderItemModel>,
    order: OrderModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        items.forEach { item ->
            val originPrice = item.price + (item.price * 0.2)
            val roundedOriginTotal = String.format(Locale.US, "%.2f", originPrice)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 8.dp),
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
        ExpandablePriceSection(order = order)
    }
}

@Composable
fun ExpandablePriceSection(
    order: OrderModel
) {
    var expanded by remember { mutableStateOf(false) }

    val taxes = order.totalPrice * 0.08
    val serviceFee = 0.99 * order.tableQuantity
    val total = order.totalPrice + taxes + serviceFee
    val roundedTotal = String.format(Locale.US, "%.2f", total)
    val roundedTaxes = String.format(Locale.US, "%.2f", taxes)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row {
                Text(
                    text = "Total amount: ",
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                )
                Text(
                    "$$roundedTotal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
            }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
//                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD3D3D3))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total price", fontSize = 13.sp)
                    Text("$${order.totalPrice}", fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Taxes(8%)", fontSize = 13.sp)
                    Text("$$roundedTaxes", fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Service Fee", fontSize = 13.sp)
                    Text("$$serviceFee", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun OrderCodeCard(
    order: OrderModel
) {
    val dateTime = order.createAt
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
    val formattedDateTime = dateTime.format(formatter) + "FDG"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
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
                    "Order code",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                val context = LocalContext.current
                val clipboardManager = context.getSystemService(ClipboardManager::class.java)

                Row {
                    Text(
                        text = formattedDateTime,
                        fontSize = 15.sp
                    )

                    Box(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .border(1.dp, color = Color.Gray, RoundedCornerShape(6.dp))
                            .clickable {
                                val clip = ClipData.newPlainText("Copied Text", formattedDateTime)
                                clipboardManager.setPrimaryClip(clip)
                            }
                    ) {
                        Text(
                            "Copy",
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Outlined.IosShare,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    "Submit a Return/Refund Request",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 24.dp)
                    .background(color = Color(0xFFD3D3D3))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.MarkUnreadChatAlt,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    "Contact Shop",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 24.dp)
                    .background(color = Color(0xFFD3D3D3))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Support,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    "Support Center",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BottomOrderBar(
    onHomeClick: () -> Unit,
    onAssess: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(0.1.dp, Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                .border(1.dp, color = Color(0xFFFF6600), shape = RoundedCornerShape(8.dp))
                .clickable { onAssess() }
        ) {
            Text(
                "Assess",
                fontSize = 18.sp,
                color = Color(0xFFFF6600),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
                .background(Color.White)
                .border(1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
                .clickable { onHomeClick() }
        ) {
            Text(
                "Home",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}