package com.example.lab1

import android.content.Context
import java.io.*

class RouteDataStorage(private val context: Context) {
    private val fileName = "route_data.txt"

    fun saveRouteData(routeData: RouteData): Boolean {
        return try {
            val allRoutes = getAllRoutes().toMutableList()
            allRoutes.add(routeData)
            saveAllRoutes(allRoutes)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllRoutes(): List<RouteData> {
        return try {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return emptyList()

            ObjectInputStream(FileInputStream(file)).use { input ->
                @Suppress("UNCHECKED_CAST")
                return input.readObject() as List<RouteData>
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun deleteRouteData(id: Long): Boolean {
        return try {
            val allRoutes = getAllRoutes().toMutableList()
            val success = allRoutes.removeIf { it.id == id }
            if (success) {
                saveAllRoutes(allRoutes)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearAll(): Boolean {
        return try {
            saveAllRoutes(emptyList())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun saveAllRoutes(routes: List<RouteData>) {
        val file = File(context.filesDir, fileName)
        ObjectOutputStream(FileOutputStream(file)).use { output ->
            output.writeObject(routes)
        }
    }
}