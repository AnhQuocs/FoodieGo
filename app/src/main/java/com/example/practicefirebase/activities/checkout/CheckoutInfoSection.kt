package com.example.practicefirebase.activities.checkout

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicefirebase.activities.order.ButtonSection
import com.example.practicefirebase.activities.order.TotalPrice
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
        CheckoutItemRow(title = "PAYMENT", value = payment)
        Divider()
        CheckoutItemRow(title = "PROMOS", value = "Apply promo code")
        Divider()
        CheckoutItemRow(title = "DATE", value = date)
        Divider()
        CheckoutItemRow(title = "TIME", value = time)
        Divider()
        ProductItem(product = product,productQuantity = productQuantity, tableQuantity = tableQuantity)
        Divider()
        TotalPrice(product = product, quantity = productQuantity, tableQuantity = tableQuantity)
        Divider()
        ButtonSection(title = "Place order", onOrderClick = onOrderClick)
    }
}

@Composable
fun CheckoutItemRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cột bên trái (nhãn)
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        // Cột bên phải (nội dung)
        Text(
            text = value,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium
        )
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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "ITEM",
                fontSize = 16.sp,
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
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "DESCRIPTION",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = product.Name,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    "Quantity: $productQuantity",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    "Number of Tables: $tableQuantity",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "PRICE",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${product.Price}",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}