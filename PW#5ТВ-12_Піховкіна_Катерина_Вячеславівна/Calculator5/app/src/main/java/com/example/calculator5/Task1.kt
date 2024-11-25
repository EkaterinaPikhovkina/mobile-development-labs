package com.example.calculator5

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Task1(navController: NavHostController, defaultValues: Map<String, String>, title: String) {
    var w1 by remember { mutableStateOf(defaultValues["w1"] ?: "") }
    var w2 by remember { mutableStateOf(defaultValues["w2"] ?: "") }
    var w3 by remember { mutableStateOf(defaultValues["w3"] ?: "") }
    var w4 by remember { mutableStateOf(defaultValues["w4"] ?: "") }
    var w5 by remember { mutableStateOf(defaultValues["w5"] ?: "") }

    var tv1 by remember { mutableStateOf(defaultValues["tв1"] ?: "") }
    var tv2 by remember { mutableStateOf(defaultValues["tв2"] ?: "") }
    var tv3 by remember { mutableStateOf(defaultValues["tв3"] ?: "") }
    var tv4 by remember { mutableStateOf(defaultValues["tв4"] ?: "") }
    var tv5 by remember { mutableStateOf(defaultValues["tв5"] ?: "") }

    var kpmax by remember { mutableStateOf(defaultValues["kп.max"] ?: "") }

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

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = w1,
                onValueChange = { w1 = it },
                label = { Text("w1") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tv1,
                onValueChange = { tv1 = it },
                label = { Text("tв1") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = w2,
                onValueChange = { w2 = it },
                label = { Text("w2") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tv2,
                onValueChange = { tv2 = it },
                label = { Text("tв2") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = w3,
                onValueChange = { w3 = it },
                label = { Text("w3") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tv3,
                onValueChange = { tv3 = it },
                label = { Text("tв3") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = w4,
                onValueChange = { w4 = it },
                label = { Text("w4") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tv4,
                onValueChange = { tv4 = it },
                label = { Text("tв4") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = w5,
                onValueChange = { w5 = it },
                label = { Text("w5") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tv5,
                onValueChange = { tv5 = it },
                label = { Text("tв5") },
                modifier = Modifier.weight(1f)
            )
        }

        TextField(
            value = kpmax,
            onValueChange = { kpmax = it },
            label = { Text("kп.max") }
        )

        Button(onClick = {
            val wos = calculateWos(
                w1.toDoubleOrNull() ?: 0.0,
                w2.toDoubleOrNull() ?: 0.0,
                w3.toDoubleOrNull() ?: 0.0,
                w4.toDoubleOrNull() ?: 0.0,
                w5.toDoubleOrNull() ?: 0.0
            )
            val wds = calculateWds(
                w1.toDoubleOrNull() ?: 0.0, tv1.toDoubleOrNull() ?: 0.0,
                w2.toDoubleOrNull() ?: 0.0, tv2.toDoubleOrNull() ?: 0.0,
                w3.toDoubleOrNull() ?: 0.0, tv3.toDoubleOrNull() ?: 0.0,
                w4.toDoubleOrNull() ?: 0.0, tv4.toDoubleOrNull() ?: 0.0,
                w5.toDoubleOrNull() ?: 0.0, tv5.toDoubleOrNull() ?: 0.0,
                kpmax.toDoubleOrNull() ?: 0.0,
            )

            result = if (wos > wds) {
                "Надійність двоколової системи електропередачі є вищою ніж одноколової."
            } else {
                "Надійність одноколової системи електропередачі є вищою ніж двоколової."
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

fun calculateWos(
    w1: Double,
    w2: Double,
    w3: Double,
    w4: Double,
    w5: Double
): Double {
    return w1 + w2 + w3 + w4 + w5
}

fun calculateWds(
    w1: Double, tv1: Double,
    w2: Double, tv2: Double,
    w3: Double, tv3: Double,
    w4: Double, tv4: Double,
    w5: Double, tv5: Double,
    kpmax: Double
): Double {
    val wos = calculateWos(w1, w2, w3, w4, w5)
    val tvos = (w1 * tv1 + w2 * tv2 + w3 * tv3 + w4 * tv4 + w5 * tv5) / wos
    val kaos = (wos * tvos) / 8760
    val kpos = (1.2 * kpmax) / 8760
    val wdk = 2 * wos * (kaos + kpos)
    val wds = wdk + 0.02

    return wds
}
