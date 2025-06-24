package com.example.practicefirebase.activities.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicefirebase.activities.Screen
import com.example.practicefirebase.activities.presentation.cart.CartScreen
import com.example.practicefirebase.activities.presentation.hisrory.HistoryScreen
import com.example.practicefirebase.activities.presentation.home.HomeScreen
import com.example.practicefirebase.activities.presentation.notification.NotificationScreen
import com.example.practicefirebase.activities.presentation.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val navigateTo = intent.getStringExtra("navigateTo")

            DashboardScreen(navController = navController)

            LaunchedEffect(navigateTo) {
                if (navigateTo == "home") {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                if(navigateTo == "cart") {
                    navController.navigate(Screen.Cart.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { MyBottomBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.History.route) { HistoryScreen() }
            composable(Screen.Notification.route) { NotificationScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}