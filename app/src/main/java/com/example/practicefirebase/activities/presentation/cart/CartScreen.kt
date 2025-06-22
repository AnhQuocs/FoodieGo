package com.example.practicefirebase.activities.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practicefirebase.domain.CartModel
import com.example.practicefirebase.viewmodel.CartViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val selectedItem = remember { mutableStateListOf<CartModel>() }
    val cartItems by cartViewModel.cartItems.observeAsState(initial = emptyList())

    val totalPrice = selectedItem.sumOf {
        it.price.replace("$", "").replace(",", ".").toDoubleOrNull() ?: 0.0
    }

    val totalFormatted = NumberFormat
        .getCurrencyInstance(Locale.US)
        .format(totalPrice)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 80.dp) // 👈 chừa chỗ cho nút Buy
        ) {
            Text(
                "My\nCart\uD83D\uDECD\uFE0F",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
            )

            if (cartItems.isEmpty()) {
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
                LazyColumn {
                    items(cartItems, key = { it.productId }) { item ->
                        CartItem(
                            cartItem = item,
                            selectedItem = selectedItem,
                            onChecked = { isChecked ->
                                if (isChecked) selectedItem.add(item)
                                else selectedItem.remove(item)
                            },
                            onDelete = { cartViewModel.removeFromCart(item) },
                            onClick = {}
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                        append("Total:")
                    }
                    append(" $totalFormatted")
                },
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* handle buy */ },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF3333)
                ),
                enabled = selectedItem.isNotEmpty()
            ) {
                Text("Buy (${selectedItem.size})")
            }
        }
    }
}