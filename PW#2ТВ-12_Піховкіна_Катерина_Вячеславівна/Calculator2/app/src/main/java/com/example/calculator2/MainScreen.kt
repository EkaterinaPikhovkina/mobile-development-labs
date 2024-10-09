package com.example.calculator2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("fuelScreen/coal") }) {
            Text("Вугілля")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("fuelScreen/mazut") }) {
            Text("Мазут")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("gas_screen") }) {
            Text("Природний газ")
        }
    }
}
