package com.example.lab1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                TrainRouteSelectionScreen()
            }
        }
    }
}

@Composable
fun TrainRouteSelectionScreen() {
    var departurePoint by remember { mutableStateOf("") }
    var arrivalPoint by remember { mutableStateOf("") }
    var selectedTimeSlot by remember { mutableStateOf("Morning") }
    var routeDetails by remember { mutableStateOf("") }

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

        // Departure Point Input
        OutlinedTextField(
            value = departurePoint,
            onValueChange = { departurePoint = it },
            label = { Text("Departure Point") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        // Arrival Point Input
        OutlinedTextField(
            value = arrivalPoint,
            onValueChange = { arrivalPoint = it },
            label = { Text("Arrival Point") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        // Time Slot Selection
        Text("Select Departure Time", style = MaterialTheme.typography.bodyLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Morning", "Afternoon", "Evening", "Night").forEach { timeSlot ->
                FilterChip(
                    selected = selectedTimeSlot == timeSlot,
                    onClick = { selectedTimeSlot = timeSlot },
                    label = { Text(timeSlot) }
                )
            }
        }

        // OK Button
        Button(
            onClick = {
                // Input Validation
                when {
                    departurePoint.isBlank() -> {
                        Toast.makeText(context, "Please enter departure point", Toast.LENGTH_SHORT).show()
                    }
                    arrivalPoint.isBlank() -> {
                        Toast.makeText(context, "Please enter arrival point", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // Construct Route Details
                        routeDetails = """
                            Route Details:
                            Departure: $departurePoint
                            Arrival: $arrivalPoint
                            Time Slot: $selectedTimeSlot
                        """.trimIndent()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Find Route")
        }

        // Route Details Display
        if (routeDetails.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(alpha = 0.2f)
                )
            ) {
                Text(
                    text = routeDetails,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}