package com.example.calculator5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "main_screen") {
                composable("main_screen") {
                    MainScreen(navController)
                }
                composable("task1") {
                    val defaults = mapOf(
                        "w1" to "0.01",
                        "w2" to "0.07",
                        "w3" to "0.015",
                        "w4" to "0.02",
                        "w5" to "0.18",
                        "tв1" to "30",
                        "tв2" to "10",
                        "tв3" to "100",
                        "tв4" to "15",
                        "tв5" to "2",
                        "kп.max" to "43"
                    )
                    Task1(navController, defaults, "Завдання 1")
                }
                composable("task2") {
                    val defaults = mapOf(
                        "w" to "0.01",
                        "tв" to "0.045",
                        "kп" to "0.004",
                        "Pм" to "5120",
                        "Tм" to "6451",
                        "Зпер.а" to "23.6",
                        "Зпер.п" to "17.6"
                    )
                    Task2(navController, defaults, "Завдання 2")
                }
            }
        }
    }
}
