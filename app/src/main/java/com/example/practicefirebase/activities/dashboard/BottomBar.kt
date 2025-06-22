package com.example.practicefirebase.activities.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.Screen

@Composable
fun MyBottomBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(R.color.white),
        tonalElevation = 3.dp,
        modifier = Modifier
            .border(1.dp, Color.LightGray.copy(alpha = 0.4f))
            .height(62.dp)
    ) {
        Screen.items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            val iconColor = if (isSelected) Color(0xFF007BFF) else colorResource(R.color.darkBrown)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!isSelected) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.label,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = screen.label,
                        fontSize = 12.sp,
                        color = iconColor,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}