package com.example.practicefirebase.activities.order

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicefirebase.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BookingDetailsSection(
    productQuantity: Int,
    onProductQuantityChange: (Int) -> Unit,
    tableQuantity: Int,
    onTableQuantityChange: (Int) -> Unit,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LineGrey()
        QuantitySection(title = "Quantity", quantity = productQuantity, onQuantityChange = onProductQuantityChange)
        LineGrey()
        QuantitySection(title = "Number of Tables", quantity = tableQuantity, onQuantityChange = onTableQuantityChange)
        LineGrey()
        DatePickerItem(label = "Booking Date", selectedDate = selectedDate, onDateSelected = onDateSelected)
        LineGrey()
        TimePickerItem(label = "Booking Time", selectedTime = selectedTime, onTimeSelected = onTimeSelected)
    }
}

@Composable
fun LineGrey() {
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.LightGray)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun QuantitySection(
    title: String,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.White)
                    .clickable(enabled = quantity > 1) { onQuantityChange(quantity - 1) }
                    .border(
                        1.dp,
                        color = if (quantity > 1) Color.Gray else Color.LightGray
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "-",
                    lineHeight = 1.sp,
                    color = if (quantity > 1) Color.Black else Color.LightGray
                )
            }

            Text(
                text = quantity.toString(),
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.White)
                    .clickable { onQuantityChange(quantity + 1) }
                    .border(1.dp, color = Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("+", lineHeight = 0.1.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun DatePickerItem(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormat = remember { SimpleDateFormat("yyy-MM-dd", Locale.getDefault()) }

    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = colorResource(R.color.lightPurple),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                android.app.DatePickerDialog(context, { _, y, m, d ->
                    calendar.set(y, m, d)
                    onDateSelected(dateFormat.format(calendar.time))
                }, year, month, day).show()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.padding(start = 12.dp).size(24.dp)
        )

        Text(
            text = "$label: $selectedDate",
            modifier = Modifier.padding(horizontal = 12.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TimePickerItem(
    label: String,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = colorResource(R.color.lightPurple),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                android.app.TimePickerDialog(context, { _, h, m ->
                    calendar.set(Calendar.HOUR_OF_DAY, h)
                    calendar.set(Calendar.MINUTE, m)
                    onTimeSelected(String.format("%02d:%02d", h, m))
                }, hour, minute, true).show()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.padding(start = 12.dp).size(24.dp)
        )

        Text(
            text = "$label: $selectedTime",
            modifier = Modifier.padding(horizontal = 12.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}