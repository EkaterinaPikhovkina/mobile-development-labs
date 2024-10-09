package com.example.calculator2

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
                composable("fuelScreen/coal") {
                    val coalDefaults = mapOf(
                        "Q_i^r" to "20.47",
                        "a_вин" to "0.8",
                        "A^r" to "25.20",
                        "Г_вин" to "1.5",
                        "n_зу" to "0.985",
                        "k_твS" to "0",
                        "B" to "1096363"
                    )
                    FuelScreen(navController, coalDefaults, "Вугілля")
                }
                composable("fuelScreen/mazut") {
                    val mazutDefaults = mapOf(
                        "Q_i^r" to "39.48",
                        "a_вин" to "1",
                        "A^r" to "0.15",
                        "Г_вин" to "0",
                        "n_зу" to "0.985",
                        "k_твS" to "0",
                        "B" to "70945"
                    )
                    FuelScreen(navController, mazutDefaults, "Мазут")
                }
                composable("gas_screen") { GasScreen() }
            }
        }
    }
}