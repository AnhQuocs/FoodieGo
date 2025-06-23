package com.example.practicefirebase.activities.presentation.checkout

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.practicefirebase.activities.presentation.order.ButtonSection
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.activities.presentation.order.TotalPrice
import com.example.practicefirebase.activities.promo.PromoActivity
import com.example.practicefirebase.domain.ProductModel

@Composable
fun CheckoutInfoSection(
    product: ProductModel,
    payment: String,
    productQuantity: Int,
    tableQuantity: Int,
    date: String,
    time: String,
    onOrderClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LineGrey()
        CheckoutItemRow(title = "PAYMENT", value = payment, color = Color.Black)
        LineGrey()
        CheckoutItemRow(title = "PROMOS", value = "Apply promo code", color = Color.Black.copy(alpha = 0.5f), hasArrow = true)
        LineGrey()
        CheckoutItemRow(title = "DATE", value = date, color = Color.Black)
        LineGrey()
        CheckoutItemRow(title = "TIME", value = time, color = Color.Black)
        LineGrey()
        ProductItem(product = product,productQuantity = productQuantity, tableQuantity = tableQuantity)
        LineGrey()
        TotalPrice(product = product, quantity = productQuantity, tableQuantity = tableQuantity)
        LineGrey()
        ButtonSection(title = "Place order", onClick = onOrderClick)
    }
}

@Composable
fun CheckoutItemRow(
    title: String,
    value: String,
    color: Color,
    hasArrow: Boolean = false
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cột bên trái (nhãn)
        Text(
            text = title,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium
        )

        // Cột bên phải (nội dung)
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                fontSize = 15.sp,
                color = color,
                style = MaterialTheme.typography.bodyMedium
            )

            if (hasArrow) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = Color.Black.copy(0.5f),
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            val intent = Intent(context, PromoActivity::class.java)
                            startActivity(context, intent, null)
                        }
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    product: ProductModel,
    productQuantity: Int,
    tableQuantity: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "ITEM",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = product.ImagePath,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "DESCRIPTION",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = product.Name,
                    fontSize = 15.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Quantity: $productQuantity",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Number of Tables: $tableQuantity",
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "PRICE",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${product.Price}",
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}