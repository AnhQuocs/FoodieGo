package com.example.practicefirebase.activities.presentation.order_from_cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.practicefirebase.activities.presentation.order.InfoItemSection
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.activities.presentation.order.Payment
import com.example.practicefirebase.activities.presentation.order.QuantitySection
import com.example.practicefirebase.activities.presentation.order.TotalItem
import com.example.practicefirebase.domain.CartModel
import com.example.practicefirebase.domain.ProductModel
import com.example.practicefirebase.ui.theme.PracticeFirebaseTheme
import java.util.Locale

@Composable
fun InfoItemFromCart(
    cartItem: CartModel,
    showItemLoading: Boolean,
    productQuantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    if (showItemLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LineGrey()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (img, name, price, des, quantity) = createRefs()

                AsyncImage(
                    model = cartItem.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                        .constrainAs(img) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = cartItem.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            start.linkTo(img.end, margin = 12.dp)
                        }
                )

                Text(
                    cartItem.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(des) {
                            top.linkTo(name.bottom, margin = 4.dp)
                            start.linkTo(name.start)
                            end.linkTo(parent.end)
                            width = Dimension.preferredWrapContent
                        }
                )

                Text(
                    text = cartItem.price,
                    color = Color(0xFFFF3333),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.constrainAs(price) {
                        top.linkTo(des.bottom, margin = 8.dp)
                        start.linkTo(des.start)
                    }
                )

                QuantityItem(
                    modifier = Modifier
                        .constrainAs(quantity) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                    quantity = productQuantity,
                    onQuantityChange = onQuantityChange
                )
            }
        }
    }
}

@Composable
fun QuantityItem(
    modifier: Modifier = Modifier,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = modifier
                    .size(20.dp)
                    .background(color = Color.White)
                    .clickable(enabled = quantity > 1) { onQuantityChange(quantity - 1) }
                    .border(
                        1.dp,
                        color = if (quantity > 1) Color.Gray else Color.LightGray
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "-",
                    lineHeight = 1.sp,
                    color = if (quantity > 1) Color.Black else Color.LightGray
                )
            }

            Text(
                text = quantity.toString(),
                fontSize = 16.sp,
                modifier = modifier.padding(horizontal = 8.dp)
            )

            Box(
                modifier = modifier
                    .size(20.dp)
                    .background(Color.White)
                    .clickable { onQuantityChange(quantity + 1) }
                    .border(1.dp, color = Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("+", lineHeight = 0.1.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun TotalPriceFromCart(
    totalPrice: Float,
    quantity: Int,
    tableQuantity: Int
) {

    val taxes = totalPrice * 0.08
    val serviceFee = 0.99 * tableQuantity
    val total = totalPrice + taxes + serviceFee

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Products",
                fontSize = 15.sp
            )

            Text(
                text = "$quantity",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TotalItem(text = "Taxes (8%)", value = taxes)
        TotalItem(text = "Service Fee ($0.99/table)", value = serviceFee)
        TotalItem(text = "Total", value = total)
    }
}