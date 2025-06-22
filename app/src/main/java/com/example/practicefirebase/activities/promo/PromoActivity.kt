package com.example.practicefirebase.activities.promo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R
import com.example.practicefirebase.activities.presentation.order.LineGrey
import com.example.practicefirebase.activities.product.product_list.TopBar

class PromoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_promo)

        setContent {
            PromoSection(onBackClick = {finish()})
        }
    }
}

@Composable
fun PromoSection(
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item {
            TopBar(
                title = "Promos",
                color = Color.Black,
                onBackClick = onBackClick,
                modifier = Modifier.height(100.dp)
            )
            LineGrey()
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.oops),
                    contentDescription = null,
                    modifier = Modifier
                        .width(500.dp)
                        .height(350.dp)
                )
                Text(
                    "You don't have any promotions yet!",
                    fontSize = 18.sp,
                    color = colorResource(R.color.blue)
                )
            }
        }
    }
}