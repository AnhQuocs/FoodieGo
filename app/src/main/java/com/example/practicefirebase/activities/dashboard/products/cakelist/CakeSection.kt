package com.example.practicefirebase.activities.dashboard.products.cakelist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicefirebase.R
import com.example.practicefirebase.domain.ProductModel

@Composable
fun CakeSection(
    cake: SnapshotStateList<ProductModel>,
    showCakeLoading: Boolean
) {
    if (showCakeLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp).padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Cake",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(end = 16.dp, top = 8.dp)
            ) {
                items(cake.size) {index ->
                    CakeItem(
                        item = cake[index],
                        onCakeClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun CakeItem(
    item: ProductModel,
    onCakeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(150.dp, 200.dp)
    ) {
        AsyncImage(
            model = item.ImagePath,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.Name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 2.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.Price,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}