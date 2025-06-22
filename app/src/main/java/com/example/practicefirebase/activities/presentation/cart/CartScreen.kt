package com.example.practicefirebase.activities.presentation.cart

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practicefirebase.activities.presentation.order.OrderActivity
import com.example.practicefirebase.activities.presentation.order_from_cart.OrderFromCartActivity
import com.example.practicefirebase.activities.product.product_detail.ProductDetailActivity
import com.example.practicefirebase.domain.CartModel
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.viewmodel.CartViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val selectedItem = remember { mutableStateListOf<CartModel>() }
    val cartItems by cartViewModel.cartItems.observeAsState(initial = emptyList())
    val isAllSelected = remember { mutableStateOf(false) }

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
                .padding(top = 16.dp, bottom = 80.dp)
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
                                isAllSelected.value = selectedItem.size == cartItems.size
                            },
                            onDelete = { cartViewModel.removeFromCart(item) },
                            onClick = {
                                val intent = Intent(context, ProductDetailActivity::class.java)
                                    .putExtra("productId", item.productId)
                                startActivity(context, intent, null)
                            }
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
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isAllSelected.value,
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = Color.Gray,
                            checkedColor = Color.Black
                        ),
                        onCheckedChange = { checked ->
                            isAllSelected.value = checked
                            selectedItem.clear()
                            if (checked) {
                                selectedItem.addAll(cartItems)
                            }
                        }
                    )
                    Text("Select All")
                }

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                            append("Total:")
                        }
                        append(" $totalFormatted")
                    },
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if(selectedItem.size == 1) {
                        val product = selectedItem.first().toProductModel()
                        val intent = Intent(context, OrderActivity::class.java).apply {
                            putExtra("product", product)
                        }
                        startActivity(context, intent, null)
                    } else {
                        val intent = Intent(context, OrderFromCartActivity::class.java).apply {
                            putExtra("productList", ArrayList(selectedItem))
                        }
                        startActivity(context, intent, null)
                    }
                },
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

fun CartModel.toProductModel(): ProductModel {
    return ProductModel(
        Id = this.productId,
        Name = this.title,
        Description = this.description,
        Price = this.price,
        ImagePath = this.image,
        CategoryId = null
    )
}