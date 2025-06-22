package com.example.practicefirebase.activities.presentation.order

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.example.practicefirebase.domain.ProductModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BookingDetailsSection(
    product: ProductModel,
    productQuantity: Int,
    onProductQuantityChange: (Int) -> Unit,
    tableQuantity: Int,
    onTableQuantityChange: (Int) -> Unit,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    selectedPayment: String,
    onPaymentSelected: (String) -> Unit,
    onOrderClick: () -> Unit
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePickerItem(label = "Booking Date", selectedDate = selectedDate, onDateSelected = onDateSelected)
            TimePickerItem(label = "Booking Time", selectedTime = selectedTime, onTimeSelected = onTimeSelected)
        }
        LineGrey()
        Payment(selectedPayment = selectedPayment, onPaymentSelected = onPaymentSelected)
        LineGrey()
        TotalPrice(product = product, quantity = productQuantity, tableQuantity = tableQuantity)
        LineGrey()
        ButtonSection(onOrderClick, title = "Order")
    }
}

@Composable
fun LineGrey(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color(0xFFEEEEEE))
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    Column(
        modifier = Modifier.width(180.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 10.dp)
        )

        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clickable {
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    android.app
                        .DatePickerDialog(context, { _, y, m, d ->
                            calendar.set(y, m, d)
                            onDateSelected(dateFormat.format(calendar.time))
                        }, year, month, day)
                        .show()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = colorResource(R.color.blue),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(24.dp)
            )

            Text(
                text = selectedDate,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = colorResource(R.color.blue),
                fontWeight = FontWeight.SemiBold
            )
        }
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

    Column(
        modifier = Modifier.width(180.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 10.dp)
        )

        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clickable {
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)

                    android.app
                        .TimePickerDialog(context, { _, h, m ->
                            calendar.set(Calendar.HOUR_OF_DAY, h)
                            calendar.set(Calendar.MINUTE, m)
                            onTimeSelected(String.format("%02d:%02d", h, m))
                        }, hour, minute, true)
                        .show()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = null,
                tint = colorResource(R.color.blue),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(24.dp)
            )

            Text(
                text = selectedTime,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = colorResource(R.color.blue),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun Payment(
    selectedPayment: String,
    onPaymentSelected: (String) -> Unit
) {
    val paymentOptions = listOf("Visa *1234", "Debit card")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            "Payment",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        paymentOptions.forEach { payment ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
                PaymentButton(
                    text = payment,
                    selected = selectedPayment == payment,
                    onSelected = { onPaymentSelected(payment) }
                )
            }
        }
    }
}

@Composable
fun PaymentButton(
    text: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, modifier = Modifier.weight(1f), fontSize = 16.sp)
        RadioButton(
            selected = selected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF3333))
        )
    }
}

@Composable
fun TotalPrice(
    product: ProductModel,
    quantity: Int,
    tableQuantity: Int
) {
    val productPrice = product.Price.replace("$", "").toDouble()
    val totalPrice = quantity * productPrice
    val taxes = totalPrice * 0.08
    val serviceFee = 0.99 * tableQuantity
    val total = totalPrice + taxes + serviceFee

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Subtotal " + "(" + quantity + ")",
                fontSize = 15.sp
            )

            Text(
                text = "$${"%.2f".format(Locale.US, totalPrice)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TotalItem(text = "Taxes (8%)", value = taxes)
        TotalItem(text = "Service Fee ($0.99/table)", value = serviceFee)
        TotalItem(text = "Total", value = total)
    }
}

@Composable
fun TotalItem(
    text: String,
    value: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontSize = 15.sp
        )

        Text(
            text = "$${"%.2f".format(Locale.US, value)}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ButtonSection(
    onOrderClick: () -> Unit,
    title: String
) {
    Button(
        onClick = { onOrderClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(44.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue)
        )
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}