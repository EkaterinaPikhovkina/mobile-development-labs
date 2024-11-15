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
fun Task1(navController: NavHostController, defaultValues: Map<String, String>, title: String) {
    var iK by remember { mutableStateOf(defaultValues["I_к"] ?: "") }
    var tF by remember { mutableStateOf(defaultValues["t_ф"] ?: "") }
    var sM by remember { mutableStateOf(defaultValues["S_м"] ?: "") }
    var tM by remember { mutableStateOf(defaultValues["T_м"] ?: "") }
    var jEk by remember { mutableStateOf(defaultValues["j_ек"] ?: "") }
    var uNom by remember { mutableStateOf(defaultValues["U_ном"] ?: "") }
    var cT by remember { mutableStateOf(defaultValues["C_т"] ?: "") }
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
            value = iK,
            onValueChange = { iK = it },
            label = { Text("I_к") }
        )

        TextField(
            value = tF,
            onValueChange = { tF = it },
            label = { Text("t_ф") }
        )

        TextField(
            value = sM,
            onValueChange = { sM = it },
            label = { Text("S_м") }
        )

        TextField(
            value = tM,
            onValueChange = { tM = it },
            label = { Text("T_м") }
        )

        TextField(
            value = jEk,
            onValueChange = { jEk = it },
            label = { Text("j_ек") }
        )

        TextField(
            value = uNom,
            onValueChange = { uNom = it },
            label = { Text("U_ном") }
        )

        TextField(
            value = cT,
            onValueChange = { cT = it },
            label = { Text("C_т") }
        )

        Button(onClick = {
            val s = calculateS(
                sM.toDoubleOrNull() ?: 0.0,
                uNom.toDoubleOrNull() ?: 0.0,
                jEk.toDoubleOrNull() ?: 0.0)
            val smin = calculateSmin(
                iK.toDoubleOrNull() ?: 0.0,
                tF.toDoubleOrNull() ?: 0.0,
                cT.toDoubleOrNull() ?: 0.0)

            result = if (s > smin) {
                "Вибираємо кабель ААБ 10 3×25 з допустимим струмом I_доп=90 А. " +
                        "Однак за термічною стійкістю до дії струмів КЗ s≤s_min," +
                        "що вимагає збільшення перерізу жил кабелю до 50 〖мм〗^2."
            } else {
                "Вибираємо кабель ААБ 10 3×25 з допустимим струмом I_доп=90 А."
            }
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}

fun calculateS(sM: Double, uNom: Double, jEk: Double): Double {
    val iM = (sM / 2) / (sqrt(3.0) * uNom)
    return iM / jEk
}

fun calculateSmin(iK: Double, tF: Double, cT: Double): Double {
    return (iK * sqrt(tF)) / cT
}



