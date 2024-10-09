package com.example.calculator2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GasScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Природний газ", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = """
            При спалюванні природного газу тверді частинки відсутні. 
            Отже, показник емісії твердих частинок k_тв при спалюванні природного газу дорівнює нулю, 
            і валовий викид твердих частинок при спалюванні природного газу також буде нульовим:
            E_тв=0т
            """.trimIndent(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
