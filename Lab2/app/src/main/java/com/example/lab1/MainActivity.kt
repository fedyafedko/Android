package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrainRouteApp()
                }
            }
        }
    }
}

@Composable
fun TrainRouteApp() {
    val showResults = remember { mutableStateOf(false) }

    val departurePoint = remember { mutableStateOf("") }
    val arrivalPoint = remember { mutableStateOf("") }
    val selectedTimeSlot = remember { mutableStateOf("Morning") }

    if (!showResults.value) {
        InputFragment(
            departurePoint = departurePoint.value,
            arrivalPoint = arrivalPoint.value,
            selectedTimeSlot = selectedTimeSlot.value,
            onDepartureChanged = { departurePoint.value = it },
            onArrivalChanged = { arrivalPoint.value = it },
            onTimeSlotChanged = { selectedTimeSlot.value = it },
            onSubmit = { showResults.value = true }
        )
    } else {
        ResultFragment(
            departurePoint = departurePoint.value,
            arrivalPoint = arrivalPoint.value,
            selectedTimeSlot = selectedTimeSlot.value,
            onCancel = {
                showResults.value = false
                departurePoint.value = ""
                arrivalPoint.value = ""
                selectedTimeSlot.value = "Morning"
            }
        )
    }
}