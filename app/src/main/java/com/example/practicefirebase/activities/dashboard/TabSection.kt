package com.example.practicefirebase.activities.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R
import com.example.practicefirebase.domain.TabModel

@Composable
@Preview
fun TabSection() {
    val tabs: List<TabModel> = listOf(
        TabModel(1, Icons.Default.FavoriteBorder, "Favorites"),
        TabModel(2, Icons.Default.History, "History"),
        TabModel(3, Icons.Default.PersonOutline, "Following"),
        TabModel(4, Icons.Default.List, "Order"),
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(tabs.size) {index ->
            val tab = tabs[index]
            TabItem(tab = tab)
        }
    }
}

@Composable
fun TabItem(tab: TabModel) {
    Box(
        modifier = Modifier
            .height(35.dp)
            .border(1.dp, colorResource(R.color.grey), RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = tab.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = tab.name,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}