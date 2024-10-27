package com.example.calculator3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlin.math.*

@Composable
fun MainScreen(navController: NavHostController, defaultValues: Map<String, String>) {

    var p_c by remember { mutableStateOf(defaultValues["P_C"] ?: "") }
    var sigma by remember { mutableStateOf(defaultValues["σ"] ?: "") }
    var b by remember { mutableStateOf(defaultValues["B"] ?: "") }
    var delta by remember { mutableStateOf(defaultValues["δ"] ?: "") }

    var result by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Калькулятор", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = p_c,
            onValueChange = { p_c = it },
            label = { Text("P_C") }
        )
        TextField(
            value = sigma,
            onValueChange = { sigma = it },
            label = { Text("σ") }
        )
        TextField(
            value = b,
            onValueChange = { b = it },
            label = { Text("B") }
        )
        TextField(
            value = delta,
            onValueChange = { delta = it },
            label = { Text("δ") }
        )

        Button(onClick = {
            val profit = calculateProfit(p_c, sigma, b, delta)

            result =
                "Відповідь: Прибуток П = %.2f тис. грн.".format(profit)
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}

fun calculateProfit(
    p_c: String,
    sigma: String,
    b: String,
    delta: String,
): Double {
    val p_c = p_c.toDoubleOrNull() ?: return 0.0
    val sigma = sigma.toDoubleOrNull() ?: return 0.0
    val b = b.toDoubleOrNull() ?: return 0.0
    val delta = delta.toDoubleOrNull() ?: return 0.0

    val pMin = p_c * (1 - delta / 100)
    val pMax = p_c * (1 + delta / 100)

    val deltaW = integrateProbabilityDensity(p_c, sigma, pMin, pMax)

    val w1 = p_c * 24 * deltaW
    val p = w1 * b

    val w2 = p_c * 24 * (1 - deltaW)
    val penalty = w2 * b

    val profit = p - penalty

    return profit
}

fun probabilityDensity(p: Double, pC: Double, sigma: Double): Double {
    val coefficient = 1 / (sigma * sqrt(2 * PI))
    val exponent = -((p - pC).pow(2)) / (2 * sigma.pow(2))
    return coefficient * exp(exponent)
}

fun integrateProbabilityDensity(
    pC: Double,
    sigma: Double,
    lowerBound: Double,
    upperBound: Double,
    stepSize: Double = 0.01
): Double {
    var sum = 0.0
    var p = lowerBound
    while (p < upperBound) {
        sum += probabilityDensity(p, pC, sigma) * stepSize
        p += stepSize
    }
    return sum
}