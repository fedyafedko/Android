package com.example.lab1

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputFragment(
    departurePoint: String,
    arrivalPoint: String,
    selectedTimeSlot: String,
    onDepartureChanged: (String) -> Unit,
    onArrivalChanged: (String) -> Unit,
    onTimeSlotChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onOpenHistory: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Train Route Selector",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = departurePoint,
            onValueChange = onDepartureChanged,
            label = { Text("Departure Point") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = arrivalPoint,
            onValueChange = onArrivalChanged,
            label = { Text("Arrival Point") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Text("Select Departure Time", style = MaterialTheme.typography.bodyLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Morning", "Afternoon", "Evening", "Night").forEach { timeSlot ->
                FilterChip(
                    selected = selectedTimeSlot == timeSlot,
                    onClick = { onTimeSlotChanged(timeSlot) },
                    label = { Text(timeSlot) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    when {
                        departurePoint.isBlank() -> {
                            Toast.makeText(context, "Please enter departure point", Toast.LENGTH_SHORT).show()
                        }
                        arrivalPoint.isBlank() -> {
                            Toast.makeText(context, "Please enter arrival point", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            onSubmit()
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("OK")
            }

            Button(
                onClick = onOpenHistory,
                modifier = Modifier.weight(1f)
            ) {
                Text("Open History")
            }
        }
    }
}
