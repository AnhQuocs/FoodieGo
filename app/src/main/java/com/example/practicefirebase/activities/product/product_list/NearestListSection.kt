package com.example.practicefirebase.activities.product.product_list

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.map.MapActivity
import com.example.practicefirebase.domain.RestaurantModel

@Composable
fun NearestListSection(
    list: SnapshotStateList<RestaurantModel>,
    showNearestLoading: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            Text(
                "Nearest Restaurants",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Text(
                "See all",
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                fontSize = 16.sp
            )
        }

        if(showNearestLoading) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            list.forEach { index ->
                NearestItems(item = index)
            }
        }
    }
}

@Composable
fun NearestItems(item: RestaurantModel) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .clickable {
                val intent = Intent(context, MapActivity::class.java).apply {
                    putExtra("object", item)
                }

                startActivity(context, intent, null)
            }
    ) {
        RestaurantImage(item = item)
        RestaurantDetail(item = item)
    }
}

@Composable
fun RestaurantDetail(item: RestaurantModel) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = item.Title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = null,
                modifier = Modifier
                    .size(14.dp)
            )

            Text(
                text = item.Address,
                color = Color.Black,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Text(
            text = item.Activity,
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Hours: ${item.Hours}",
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun RestaurantImage(item: RestaurantModel) {
    AsyncImage(
        model = item.ImagePath,
        contentDescription = null,
        modifier = Modifier
            .padding(8.dp)
            .size(95.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(R.color.grey), shape = RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}