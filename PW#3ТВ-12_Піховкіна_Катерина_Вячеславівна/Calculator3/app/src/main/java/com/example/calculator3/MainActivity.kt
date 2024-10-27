package com.example.calculator3

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
                    val defaults = mapOf(
                        "P_C" to "5",
                        "σ" to "0.25",
                        "B" to "7",
                        "δ" to "5",
                    )
                    MainScreen(navController, defaults)
                }
            }
        }
    }
}