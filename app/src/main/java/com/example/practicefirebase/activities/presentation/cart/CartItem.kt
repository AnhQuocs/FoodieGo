package com.example.practicefirebase.activities.presentation.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicefirebase.domain.cart.CartModel
import java.util.Locale

@Composable
fun CartItem(
    cartItem: CartModel,
    selectedItem: MutableList<CartModel>,
    onChecked: (Boolean) -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val priceFloat = cartItem.price
        .replace("$", "")
        .replace(",", ".")
        .toFloat()

    val originPrice = priceFloat + (priceFloat * 0.2)
    val roundedPrice = String.format(Locale.US, "%.2f", originPrice).toFloat()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = 6.dp)
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = selectedItem.contains(cartItem),
                onCheckedChange = { isChecked -> onChecked(isChecked) },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.LightGray,
                    checkedColor = Color.Black
                )
            )

            AsyncImage(
                model = cartItem.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = cartItem.price,
                        fontSize = 13.sp,
                        color = Color.Black.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "$" + roundedPrice.toString(),
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }

                Text(
                    text = "x1",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            IconButton(
                onClick = { onDelete() }
            ) {
                Icon(
                    Icons.Default.DeleteSweep,
                    contentDescription = null,
                    tint = Color(0xFFFF3333)
                )
            }
        }
    }
}