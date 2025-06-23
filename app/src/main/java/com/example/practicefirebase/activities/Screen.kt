package com.example.practicefirebase.activities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Cart : Screen("cart", "Cart", Icons.Default.ShoppingCart)
    object History : Screen("history", "History", Icons.Default.History)
    object Notification : Screen("notification", "Notification", Icons.Default.Notifications)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    companion object {
        val items = listOf(Home, Cart, History, Notification, Profile)
    }
}
