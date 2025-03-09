package com.example.lab1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.Lab1Theme

class HistoryActivity : ComponentActivity() {
    private lateinit var storage: RouteDataStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = RouteDataStorage(this)

        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoryScreen(
                        storage = storage,
                        onBackPressed = { finish() },
                        onShowToast = { message ->
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    storage: RouteDataStorage,
    onBackPressed: () -> Unit,
    onShowToast: (String) -> Unit
) {
    val routes = remember { mutableStateOf(storage.getAllRoutes()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History of routes") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val success = storage.clearAll()
                            if (success) {
                                routes.value = emptyList()
                                onShowToast("All routes deleted")
                            }
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Clear All")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (routes.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Routes not found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(routes.value) { route ->
                    RouteCard(
                        route = route,
                        onDelete = {
                            val success = storage.deleteRouteData(route.id)
                            if (success) {
                                routes.value = storage.getAllRoutes()
                                onShowToast("The route deleted")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RouteCard(
    route: RouteData,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = """
                    Departure: ${route.departurePoint}
                    Arrival: ${route.arrivalPoint}
                    Time Slot: ${route.timeSlot}
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Remove")
            }
        }
    }
}