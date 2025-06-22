package com.example.practicefirebase.activities.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicefirebase.activities.Screen
import com.example.practicefirebase.activities.presentation.cart.CartScreen
import com.example.practicefirebase.activities.screen_test.DiscoverScreen
import com.example.practicefirebase.activities.presentation.home.HomeScreen
import com.example.practicefirebase.activities.screen_test.NotificationScreen
import com.example.practicefirebase.activities.screen_test.ProfileScreen
import com.example.practicefirebase.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardScreen()
        }
    }
}

@Composable
fun DashboardScreen() {

    val viewmodel = MainViewModel()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController = navController) }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Discover.route) { DiscoverScreen() }
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.Notification.route) { NotificationScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}