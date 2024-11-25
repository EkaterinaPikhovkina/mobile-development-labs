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
fun Task2(navController: NavHostController, defaultValues: Map<String, String>, title: String) {
    var w by remember { mutableStateOf(defaultValues["w"] ?: "") }
    var tv by remember { mutableStateOf(defaultValues["tв"] ?: "") }
    var kp by remember { mutableStateOf(defaultValues["kп"] ?: "") }
    var pm by remember { mutableStateOf(defaultValues["Pм"] ?: "") }
    var tm by remember { mutableStateOf(defaultValues["Tм"] ?: "") }
    var z_per_a by remember { mutableStateOf(defaultValues["Зпер.а"] ?: "") }
    var z_per_p by remember { mutableStateOf(defaultValues["Зпер.п"] ?: "") }

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
                value = w,
                onValueChange = { w = it },
                label = { Text("w") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tv,
                onValueChange = { tv = it },
                label = { Text("tв") },
                modifier = Modifier.weight(1f)
            )
        }

        TextField(
            value = kp,
            onValueChange = { kp = it },
            label = { Text("kп") }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = pm,
                onValueChange = { pm = it },
                label = { Text("Pм") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = tm,
                onValueChange = { tm = it },
                label = { Text("Tм") },
                modifier = Modifier.weight(1f)
            )
        }

        TextField(
            value = z_per_a,
            onValueChange = { z_per_a = it },
            label = { Text("Зпер.а") }
        )

        TextField(
            value = z_per_p,
            onValueChange = { z_per_p = it },
            label = { Text("Зпер.п") }
        )

        Button(onClick = {
            val mzper = calculateMZper(
                w.toDoubleOrNull() ?: 0.0,
                tv.toDoubleOrNull() ?: 0.0,
                kp.toDoubleOrNull() ?: 0.0,
                pm.toDoubleOrNull() ?: 0.0,
                tm.toDoubleOrNull() ?: 0.0,
                z_per_a.toDoubleOrNull() ?: 0.0,
                z_per_p.toDoubleOrNull() ?: 0.0
            )
            result = ("Математичне сподівання збитків від переривання електропостачання " +
                    "М(Зпер) = %.2f грн.").format(mzper)
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}


fun calculateMZper(
    w: Double,
    tv: Double,
    kp: Double,
    pm: Double,
    tm: Double,
    z_per_a: Double,
    z_per_p: Double
): Double {
    val mw_neda = w * tv * pm * tm
    val mw_nedp = kp * pm * tm
    val mz_per = z_per_a * mw_neda + z_per_p * mw_nedp

    return mz_per
}