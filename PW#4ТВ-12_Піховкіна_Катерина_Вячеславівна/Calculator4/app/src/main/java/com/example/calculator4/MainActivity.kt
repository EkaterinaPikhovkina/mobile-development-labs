package com.example.calculator4

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
                        "I_к" to "2.5",
                        "t_ф" to "2.5",
                        "S_м" to "1300",
                        "T_м" to "4000",
                        "j_ек" to "1.4",
                        "U_ном" to "10",
                        "C_т" to "92"
                    )
                    Task1(navController, defaults, "Завдання 1")
                }
                composable("task2") {
                    val defaults = mapOf(
                        "U_с.н" to "10.5",
                        "S_к" to "200",
                        "U_к%" to "10.5",
                        "S_ном.т" to "6.3"
                    )
                    Task2(navController, defaults, "Завдання 2")
                }
                composable("task3") {
                    val defaults = mapOf(
                        "U_к.max" to "11.1",
                        "U_в.н" to "115",
                        "U_н.н" to "11",
                        "S_ном.т" to "6.3",
                        "R_с.н" to "10.65",
                        "X_с.н" to "24.02",
                        "R_с.min" to "34.88",
                        "X_с.min" to "65.68",
                        "l_л" to "12.37",
                        "R_0" to "0.64",
                        "X_0" to "0.363"
                    )
                    Task3(navController, defaults, "Завдання 3")
                }
            }
        }
    }
}