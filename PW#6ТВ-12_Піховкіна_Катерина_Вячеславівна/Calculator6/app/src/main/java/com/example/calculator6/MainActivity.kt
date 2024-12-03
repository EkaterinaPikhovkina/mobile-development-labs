package com.example.calculator6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerCalculatorApp()
        }
    }
}

@Composable
fun PowerCalculatorApp() {
    val rows = 8

    var nominalEfficiency by remember { mutableStateOf(List(rows) { "0.92" }) }
    var loadPowerFactor by remember { mutableStateOf(List(rows) { "0.9" }) }
    var loadVoltage by remember { mutableStateOf(List(rows) { "0.38" }) }
    var units by remember { mutableStateOf(listOf("4", "2", "4", "1", "1", "1", "1", "2")) }
    var nominalPower by remember { mutableStateOf(listOf("20", "14", "42", "36", "20", "40", "32", "20")) }
    var utilizationFactor by remember { mutableStateOf(listOf("0.15", "0.12", "0.15", "0.3", "0.5", "0.2", "0.2", "0.65")) }
    var reactivePowerFactor by remember { mutableStateOf(listOf("1.33", "1", "1.33", "1.52", "0.75", "1", "1", "0.75")) }

    var groupUtilizationRate by remember { mutableDoubleStateOf(0.0) }
    var effectiveNumberOfUnits by remember { mutableDoubleStateOf(0.0) }
    var estimatedActivePowerFactor by remember { mutableDoubleStateOf(0.0) }
    var estimatedActiveLoad by remember { mutableDoubleStateOf(0.0) }
    var estimatedReactiveLoad by remember { mutableDoubleStateOf(0.0) }
    var fullPower by remember { mutableDoubleStateOf(0.0) }
    var estimatedGroupCurrent by remember { mutableDoubleStateOf(0.0) }

    val calculateValues = {
        val n = units.map { it.toInt() }
        val pN = nominalPower.map { it.toDouble() }
        val kV = utilizationFactor.map { it.toDouble() }
        val tgPhi = reactivePowerFactor.map { it.toDouble() }

        val totalPower = n.zip(pN).sumOf { it.first * it.second }
        val totalWeightedPower = n.zip(pN).zip(kV).sumOf { (np, k) -> np.first * np.second * k }
        groupUtilizationRate = totalWeightedPower / totalPower

        val powerSquared = totalPower.pow(2)
        val sumPowerSquared = n.zip(pN).sumOf { (it.first * it.second).pow(2) }
        effectiveNumberOfUnits = powerSquared / sumPowerSquared

        estimatedActivePowerFactor = 1.25
        estimatedActiveLoad = estimatedActivePowerFactor * totalWeightedPower
        estimatedReactiveLoad = groupUtilizationRate * totalPower * tgPhi[0]
        fullPower = sqrt(estimatedActiveLoad.pow(2) + estimatedReactiveLoad.pow(2))
        estimatedGroupCurrent = estimatedActiveLoad / loadVoltage[0].toDouble()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Power Calculator", fontSize = 24.sp, modifier = Modifier.padding(8.dp))

        Table(
            headers = listOf("η", "cos φ", "U", "n", "P_n", "K_B", "tg φ"),
            data = listOf(
                nominalEfficiency,
                loadPowerFactor,
                loadVoltage,
                units,
                nominalPower,
                utilizationFactor,
                reactivePowerFactor
            )
        ) { columnIndex, rowIndex, value ->
            when (columnIndex) {
                0 -> nominalEfficiency = nominalEfficiency.toMutableList().apply { set(rowIndex, value) }
                1 -> loadPowerFactor = loadPowerFactor.toMutableList().apply { set(rowIndex, value) }
                2 -> loadVoltage = loadVoltage.toMutableList().apply { set(rowIndex, value) }
                3 -> units = units.toMutableList().apply { set(rowIndex, value) }
                4 -> nominalPower = nominalPower.toMutableList().apply { set(rowIndex, value) }
                5 -> utilizationFactor = utilizationFactor.toMutableList().apply { set(rowIndex, value) }
                6 -> reactivePowerFactor = reactivePowerFactor.toMutableList().apply { set(rowIndex, value) }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = calculateValues, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Results:", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        Text("Group utilization rate: K_c = %.4f".format(groupUtilizationRate))
        Text("Effective number of units: n_e = %.4f".format(effectiveNumberOfUnits))
        Text("Estimated active power factor: K_p = %.4f".format(estimatedActivePowerFactor))
        Text("Estimated active load: P_r = %.4f kW".format(estimatedActiveLoad))
        Text("Estimated reactive load: Q_r = %.4f kvar".format(estimatedReactiveLoad))
        Text("Full power: S_p = %.4f kVA".format(fullPower))
        Text("Estimated group current: I_p = %.4f A".format(estimatedGroupCurrent))
    }
}

@Composable
fun Table(
    headers: List<String>,
    data: List<List<String>>,
    onValueChange: (Int, Int, String) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            headers.forEach { header ->
                Text(
                    text = header,
                    modifier = Modifier.weight(1f).padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        data[0].indices.forEach { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                data.forEachIndexed { columnIndex, columnData ->
                    BasicTextField(
                        value = columnData[rowIndex],
                        onValueChange = { onValueChange(columnIndex, rowIndex, it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}
