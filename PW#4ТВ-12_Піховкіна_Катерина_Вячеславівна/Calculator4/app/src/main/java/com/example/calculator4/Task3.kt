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
fun Task3(navController: NavHostController, defaultValues: Map<String, String>, title: String) {
    var ukMax by remember { mutableStateOf(defaultValues["U_к.max"] ?: "") }
    var uvn by remember { mutableStateOf(defaultValues["U_в.н"] ?: "") }
    var unn by remember { mutableStateOf(defaultValues["U_н.н"] ?: "") }
    var rSn by remember { mutableStateOf(defaultValues["R_с.н"] ?: "") }
    var xSn by remember { mutableStateOf(defaultValues["X_с.н"] ?: "") }
    var rSmin by remember { mutableStateOf(defaultValues["R_с.min"] ?: "") }
    var xSmin by remember { mutableStateOf(defaultValues["X_с.min"] ?: "") }
    var ll by remember { mutableStateOf(defaultValues["l_л"] ?: "") }
    var r0 by remember { mutableStateOf(defaultValues["R_0"] ?: "") }
    var x0 by remember { mutableStateOf(defaultValues["X_0"] ?: "") }
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

        TextField(value = ukMax, onValueChange = { ukMax = it }, label = { Text("U_к.max") })
        TextField(value = uvn, onValueChange = { uvn = it }, label = { Text("U_в.н") })
        TextField(value = unn, onValueChange = { unn = it }, label = { Text("U_н.н") })
        TextField(value = rSn, onValueChange = { rSn = it }, label = { Text("R_с.н") })
        TextField(value = xSn, onValueChange = { xSn = it }, label = { Text("X_с.н") })
        TextField(value = rSmin, onValueChange = { rSmin = it }, label = { Text("R_с.min") })
        TextField(value = xSmin, onValueChange = { xSmin = it }, label = { Text("X_с.min") })
        TextField(value = ll, onValueChange = { ll = it }, label = { Text("l_л") })
        TextField(value = r0, onValueChange = { r0 = it }, label = { Text("R_0") })
        TextField(value = x0, onValueChange = { x0 = it }, label = { Text("X_0") })

        Button(onClick = {
            val iln = calculateIln(ukMax.toDouble(), uvn.toDouble(), unn.toDouble(), rSn.toDouble(), xSn.toDouble(), rSmin.toDouble(), xSmin.toDouble(), ll.toDouble(), r0.toDouble(), x0.toDouble())
            result = (
                    "Струми трифазного та двофазного КЗ в точці 10 в нормальному та мінімальному режимах:\n" +
                            "I_(л.н)^((3) )=%.2f А\n".format(iln[0]) +
                            "I_(л.н)^((2) )=%.2f А\n".format(iln[1]) +
                            "I_(л.н.min)^((3) )=%.2f А\n".format(iln[2]) +
                            "I_(л.н.min)^((2) )=%.2f А\n".format(iln[3]) +
                            "Аварійний режим на станції не передбачений, оскільки вона живить споживачів 3 категорії.")
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}

fun calculateIln(ukMax: Double, uvn: Double, unn: Double, rSn: Double, xSn: Double, rSmin: Double,
                 xSmin: Double, ll: Double, r0: Double, x0: Double): List<Double> {
    val xt = ukMax / 100 * (uvn * uvn) / 6.3
    val xSh = xSn + xt
    val xShMin = xSmin + xt

    val kPr = (unn * unn) / (uvn * uvn)

    val rShN = rSn * kPr
    val xShN = xSh * kPr

    val rL = ll * r0
    val xL = ll * x0

    val rSigmaN = rL + rShN
    val xSigmaN = xL + xShN
    val zSigmaN = sqrt(rSigmaN * rSigmaN + xSigmaN * xSigmaN)

    val iLn3 = (unn * 1000) / (sqrt(3.0) * zSigmaN)
    val iLn2 = iLn3 * sqrt(3.0) / 2

    val rSigmaMin = rL + (rSmin * kPr)
    val xSigmaMin = xL + (xShMin * kPr)
    val zSigmaMin = sqrt(rSigmaMin * rSigmaMin + xSigmaMin * xSigmaMin)

    val iLnMin3 = (unn * 1000) / (sqrt(3.0) * zSigmaMin)
    val iLnMin2 = iLnMin3 * sqrt(3.0) / 2

    return listOf(iLn3, iLn2, iLnMin3, iLnMin2)
}





