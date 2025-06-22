package com.example.practicefirebase.activities.product.product_detail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.order.OrderActivity
import com.example.practicefirebase.activities.product.product_list.TopBar
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.viewmodel.CartViewModel

@Composable
fun ProductDetailSection(
    product: ProductModel,
    showProductDetailLoading: Boolean,
    onBackClick: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var lastToastTime by remember { mutableStateOf(0L) }

    TopBar(
        onBackClick = onBackClick,
        modifier = Modifier
            .height(80.dp)
            .background(color = Color.White),
        title = "Food Detail",
        color = Color.Black
    )

    LaunchedEffect(cartViewModel.cartEvent) {
        cartViewModel.cartEvent.collect {message ->
            val currentTime = System.currentTimeMillis()
            if(currentTime - lastToastTime > 2000) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                lastToastTime = currentTime
            }
        }
    }

    if (showProductDetailLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = product.ImagePath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.Name,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.Price,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFF3333)
                )
                Text(
                    "(VAT not included)",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⭐ ⭐ ⭐ ⭐ ⭐")
                Text(
                    "See all 120 reviews",
                    fontSize = 16.sp,
                    color = colorResource(R.color.blue),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Description",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            ReadMoreText(product)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Condition: ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Available for pre-order",
                    fontSize = 18.sp,
                    color = Color(0xFF4CAF50)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val intent = Intent(context, OrderActivity::class.java).apply {
                            putExtra("product", product)
                        }
                        startActivity(context, intent, null)
                    },
                    modifier = Modifier
                        .weight(0.8f)
                        .height(40.dp)
                        .align(Alignment.Bottom),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.blue)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        "Order",
                        fontSize = 18.sp
                    )
                }

                var isFavorite by remember { mutableStateOf(true) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(0.4f)
                ) {
                    IconButton(
                        onClick = {
                            cartViewModel.addToCart(product)
                        },
                        modifier = Modifier.weight(0.2f)
                    ) {
                        Icon(
                            Icons.Default.AddShoppingCart,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            isFavorite = !isFavorite
                        },
                        modifier = Modifier.weight(0.2f)
                    ) {
                        Icon(
                            if(isFavorite)Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReadMoreText(product: ProductModel) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            "${product.Description}",
            fontSize = 16.sp,
            maxLines = if (expanded) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (expanded) "Show less" else "Read more",
            fontSize = 16.sp,
            color = colorResource(R.color.blue),
            modifier = Modifier.clickable { expanded = !expanded }
        )
    }
}