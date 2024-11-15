package com.example.calculator4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlin.math.sqrt

@Composable
fun Task2(navController: NavHostController, defaultValues: Map<String, String>, title: String) {
    var uSn by remember { mutableStateOf(defaultValues["U_с.н"] ?: "") }
    var sk by remember { mutableStateOf(defaultValues["S_к"] ?: "") }
    var ukPercentage by remember { mutableStateOf(defaultValues["U_к%"] ?: "") }
    var sNomT by remember { mutableStateOf(defaultValues["S_ном.т"] ?: "") }
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
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uSn,
            onValueChange = { uSn = it },
            label = { Text("U_с.н") }
        )

        TextField(
            value = sk,
            onValueChange = { sk = it },
            label = { Text("S_к") }
        )

        TextField(
            value = ukPercentage,
            onValueChange = { ukPercentage = it },
            label = { Text("U_к%") }
        )

        TextField(
            value = sNomT,
            onValueChange = { sNomT = it },
            label = { Text("S_ном.т") }
        )

        Button(onClick = {
            val ip0 = calculateIp0(
                uSn.toDoubleOrNull() ?: 0.0,
                sk.toDoubleOrNull() ?: 0.0,
                ukPercentage.toDoubleOrNull() ?: 0.0,
                sNomT.toDoubleOrNull() ?: 0.0
            )
            result = "Початкове діюче значення струму трифазного КЗ дорівнює %.2f кА.".format(ip0)
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}

fun calculateIp0(uSn: Double, sk: Double, ukPercentage: Double, sNomT: Double): Double {
    val xC = (uSn * uSn) / sk
    val xT = (ukPercentage / 100) * (uSn * uSn) / sNomT
    val xTotal = xC + xT
    return uSn / (sqrt(3.0) * xTotal)
}



