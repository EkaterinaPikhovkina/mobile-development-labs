package com.example.calculator2

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
fun FuelScreen(navController: NavHostController, defaultValues: Map<String, String>, title: String) {
    var qRi by remember { mutableStateOf(defaultValues["Q_i^r"] ?: "") }
    var aVin by remember { mutableStateOf(defaultValues["a_вин"] ?: "") }
    var ar by remember { mutableStateOf(defaultValues["A^r"] ?: "") }
    var gVin by remember { mutableStateOf(defaultValues["Г_вин"] ?: "") }
    var nZu by remember { mutableStateOf(defaultValues["n_зу"] ?: "") }
    var ktvS by remember { mutableStateOf(defaultValues["k_твS"] ?: "") }
    var b by remember { mutableStateOf(defaultValues["B"] ?: "") }
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
            value = qRi,
            onValueChange = { qRi = it },
            label = { Text("Q_i^r") }
        )
        TextField(
            value = aVin,
            onValueChange = { aVin = it },
            label = { Text("a_вин") }
        )
        TextField(
            value = ar,
            onValueChange = { ar = it },
            label = { Text("A^r") }
        )
        TextField(
            value = gVin,
            onValueChange = { gVin = it },
            label = { Text("Г_вин") }
        )
        TextField(
            value = nZu,
            onValueChange = { nZu = it },
            label = { Text("n_зу") }
        )
        TextField(
            value = ktvS,
            onValueChange = { ktvS = it },
            label = { Text("k_твS") }
        )
        TextField(
            value = b,
            onValueChange = { b = it },
            label = { Text("B") }
        )

        Button(onClick = {
            val ktv = calculateKtv(qRi, aVin, ar, gVin, nZu, ktvS)
            val etv = calculateEtv(qRi, ktv, b)
            result =
                "Показник емісії твердих частинок при спалюванні становитиме: k_тв = %.2f г/ГДж;\n".format(ktv) +
                        "Валовий викид при спалюванні становитиме: E_тв = %.2f т;".format(etv)
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}

fun calculateKtv(
    qVug: String,
    aVin: String,
    ar: String,
    gVin: String,
    nZu: String,
    ktvS: String
): Double {
    val qRi = qVug.toDoubleOrNull() ?: return 0.0
    val aVin = aVin.toDoubleOrNull() ?: return 0.0
    val ar = ar.toDoubleOrNull() ?: return 0.0
    val gVin = gVin.toDoubleOrNull() ?: return 0.0
    val nZu = nZu.toDoubleOrNull() ?: return 0.0
    val ktvS = ktvS.toDoubleOrNull() ?: return 0.0

    return (1_000_000 / qRi) * (aVin * ar / (100 - gVin)) * (1 - nZu) + ktvS
}

fun calculateEtv(qRi: String, ktv: Double, b: String): Double {
    val qRi = qRi.toDoubleOrNull() ?: return 0.0
    val b = b.toDoubleOrNull() ?: return 0.0

    return 1e-6 * ktv * qRi * b
}
