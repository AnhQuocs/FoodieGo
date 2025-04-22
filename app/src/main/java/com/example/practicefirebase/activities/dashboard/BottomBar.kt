package com.example.practicefirebase.activities.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R

@Composable
@Preview
fun MyBottomBar() {
    val bottomMenuItemList = prepareBottomMenu()
    val context = LocalContext.current
    var selected by remember { mutableStateOf("Home") }

    BottomAppBar(
        backgroundColor = colorResource(R.color.white),
        elevation = 3.dp
    ) {
        bottomMenuItemList.forEach {bottomMenuItems ->
            BottomNavigationItem(
                selected = (selected == bottomMenuItems.label),
                onClick = {
                    selected = bottomMenuItems.label
                    Toast.makeText(context, bottomMenuItems.label, Toast.LENGTH_SHORT).show()
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = bottomMenuItems.icon,
                            contentDescription = null,
                            tint = colorResource(R.color.darkBrown),
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(20.dp)
                        )

                        Text(
                            text = bottomMenuItems.label,
                            fontSize = 12.sp,
                            color = colorResource(R.color.darkBrown),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            )
        }
    }
}

data class BottomMenuItem(
    val label: String,
    val icon: ImageVector
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(BottomMenuItem(label = "Home", icon = Icons.Default.Home))
    bottomMenuItemList.add(BottomMenuItem(label = "Discover", icon = Icons.Default.Explore))
    bottomMenuItemList.add(BottomMenuItem(label = "Cart", icon = Icons.Default.ShoppingCart))
    bottomMenuItemList.add(BottomMenuItem(label = "Notification", icon = Icons.Default.Notifications))
    bottomMenuItemList.add(BottomMenuItem(label = "Profile", icon = Icons.Default.Person))

    return bottomMenuItemList
}