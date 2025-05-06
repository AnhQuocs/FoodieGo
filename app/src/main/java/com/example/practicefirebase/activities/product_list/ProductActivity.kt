package com.example.practicefirebase.activities.product_list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicefirebase.R

class ProductActivity : AppCompatActivity() {
    private var categoryId: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        categoryId = intent.getStringExtra("categoryId")?: ""

        setContent {
            ProductList(categoryId, onBackClick = {finish()})
        }
    }
}

@Composable
fun ProductList(
    categoryId: String,
    onBackClick: () -> Unit
) {
    
}