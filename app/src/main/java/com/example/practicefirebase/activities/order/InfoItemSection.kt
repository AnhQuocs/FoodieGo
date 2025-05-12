package com.example.practicefirebase.activities.order

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.practicefirebase.domain.ProductModel

@Composable
fun InfoItemSection(
    product: ProductModel,
    showItemLoading: Boolean
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
                .padding(horizontal = 16.dp).padding(top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (img, name, price, des) = createRefs()

                AsyncImage(
                    model = product.ImagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
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
                    text = product.Name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            start.linkTo(img.end, margin = 12.dp)
                        }
                )

                Text(
                    "Special dishes are waiting for you to discover!",
                    fontSize = 15.sp,
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
                    text = product.Price,
                    color = Color(0xFFFF3333),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.constrainAs(price) {
                        top.linkTo(des.bottom, margin = 8.dp)
                        start.linkTo(des.start)
                    }
                )
            }
        }
    }
}