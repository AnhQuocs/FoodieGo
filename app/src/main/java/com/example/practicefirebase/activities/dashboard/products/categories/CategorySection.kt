package com.example.practicefirebase.activities.dashboard.products.categories

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.product_list.ProductActivity
import com.example.practicefirebase.domain.CategoryModel

@Composable
fun CategorySection(
    category: SnapshotStateList<CategoryModel>,
    showCategoryLoading: Boolean
) {
    if(showCategoryLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Food list",
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
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(end = 16.dp, top = 8.dp)
            ) {
                items(category.size) {index ->
                    Category(
                        item = category[index],
                        onItemClick = {
                            val intent = Intent(context, ProductActivity::class.java).apply {
                                putExtra("categoryId", category[index].Id.toString())
                                Log.d("CategoryId", "Category Id = ${category[index].Id}")
                            }
                            Log.d("CategoryId", "Intent Extra: ${intent.extras}")
                            startActivity(context, intent, null)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Category(
    item: CategoryModel,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(85.dp, 90.dp)
            .clickable { onItemClick() }
            .background(color = colorResource(R.color.blue), shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = item.ImagePath,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 6.dp)
                .size(55.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
        )

        Text(
            text = item.Name,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}