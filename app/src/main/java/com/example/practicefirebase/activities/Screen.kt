package com.example.practicefirebase.activities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Discover : Screen("discover", "Discover", Icons.Default.Explore)
    object Cart : Screen("cart", "Cart", Icons.Default.ShoppingCart)
    object Notification : Screen("notification", "Notification", Icons.Default.Notifications)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    companion object {
        val items = listOf(Home, Discover, Cart, Notification, Profile)
    }
}
