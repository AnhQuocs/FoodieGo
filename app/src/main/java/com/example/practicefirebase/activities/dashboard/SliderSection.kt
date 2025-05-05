package com.example.practicefirebase.activities.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun SliderSection() {
    val sliderItemList = prepareSliderItem()
    val pagerState = rememberPagerState(initialPage = 0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp)
            .background(color = colorResource(R.color.white), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
    ) {
        HorizontalPager(
            count = sliderItemList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) {page ->
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = sliderItemList[page].image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp))
                )

                Text(
                    text = sliderItemList[page].title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.8f),
                            offset = Offset(2f, 2f),
                            blurRadius = 10f
                        )
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            activeColor = Color.Black,
            inactiveColor = Color.Gray,
            indicatorWidth = 6.dp,       // Chiều rộng mỗi chấm
            indicatorHeight = 6.dp,      // Chiều cao mỗi chấm
            spacing = 8.dp
        )
    }
}

data class SliderItem(
    val image: Painter,
    val title: String
)

@Composable
fun prepareSliderItem(): List<SliderItem> {
    val sliderItemList = arrayListOf<SliderItem>()

    sliderItemList.add(SliderItem(image = painterResource(R.drawable.food_slider), title = "Dessert"))
    sliderItemList.add(SliderItem(image = painterResource(R.drawable.cream_slider), title = "Ice Cream"))
    sliderItemList.add(SliderItem(image = painterResource(R.drawable.seafood_slider), title = "Seafood"))
    sliderItemList.add(SliderItem(image = painterResource(R.drawable.fastfoodslider), title = "Fast food"))
    sliderItemList.add(SliderItem(image = painterResource(R.drawable.cake_slider), title = "Cake"))

    return sliderItemList
}