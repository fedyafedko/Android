package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    private lateinit var storage: RouteDataStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = RouteDataStorage(this)

        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrainRouteApp(
                        storage = storage,
                        onOpenHistory = {
                            val intent = Intent(this, HistoryActivity::class.java)
                            startActivity(intent)
                        },
                        onShowToast = { message ->
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TrainRouteApp(
    storage: RouteDataStorage,
    onOpenHistory: () -> Unit,
    onShowToast: (String) -> Unit
) {
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
            onSubmit = {
                val routeData = RouteData(
                    departurePoint = departurePoint.value,
                    arrivalPoint = arrivalPoint.value,
                    timeSlot = selectedTimeSlot.value
                )

                val success = storage.saveRouteData(routeData)
                if (success) {
                    onShowToast("Маршрут успішно збережено!")
                } else {
                    onShowToast("Помилка при збереженні маршруту!")
                }

                showResults.value = true
            },
            onOpenHistory = onOpenHistory
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