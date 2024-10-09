package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculate() }
    }

    private fun calculate() {
        val hp = binding.inputHP.text.toString().toDoubleOrNull() ?: 0.0
        val cp = binding.inputCP.text.toString().toDoubleOrNull() ?: 0.0
        val sp = binding.inputSP.text.toString().toDoubleOrNull() ?: 0.0
        val np = binding.inputNP.text.toString().toDoubleOrNull() ?: 0.0
        val op = binding.inputOP.text.toString().toDoubleOrNull() ?: 0.0
        val wp = binding.inputWP.text.toString().toDoubleOrNull() ?: 0.0
        val ap = binding.inputAP.text.toString().toDoubleOrNull() ?: 0.0

        // 1. Розрахунок коефіцієнтів переходу
        val krs = 100 / (100 - wp)
        val krg = 100 / (100 - wp - ap)

        // 2. Розрахунок складу сухої маси палива
        val hS = hp * krs
        val cS = cp * krs
        val sS = sp * krs
        val nS = np * krs
        val oS = op * krs
        val aS = ap * krs

        // 3. Розрахунок складу горючої маси палива
        val hG = hp * krg
        val cG = cp * krg
        val sG = sp * krg
        val nG = np * krg
        val oG = op * krg

        // 4. Розрахунок нижчої теплоти згорання для робочої маси
        val qrp = 339 * cp + 1030 * hp - 108.8 * (op - sp) - 25 * wp

        // 5. Перерахунок теплоти на суху масу
        val qch = (qrp + 0.025 * wp) * (100 / (100 - wp))

        // 6. Перерахунок теплоти на горючу масу
        val qgh = (qrp + 0.025 * wp) * (100 / (100 - wp - ap))

        // Округлення значень до двох знаків після коми
        val roundedKrs = String.format("%.2f", krs)
        val roundedKrg = String.format("%.2f", krg)
        val roundedHS = String.format("%.2f", hS)
        val roundedCS = String.format("%.2f", cS)
        val roundedSS = String.format("%.2f", sS)
        val roundedNS = String.format("%.2f", nS)
        val roundedOS = String.format("%.2f", oS)
        val roundedAS = String.format("%.2f", aS)
        val roundedHG = String.format("%.2f", hG)
        val roundedCG = String.format("%.2f", cG)
        val roundedSG = String.format("%.2f", sG)
        val roundedNG = String.format("%.2f", nG)
        val roundedOG = String.format("%.2f", oG)

        // Перетворення теплоти з кДж/кг на МДж/кг та округлення
        val roundedQrp = String.format("%.2f", qrp / 1000)
        val roundedQch = String.format("%.2f", qch / 1000)
        val roundedQgh = String.format("%.2f", qgh / 1000)

        // Виведення результатів на екран
        val resultText = getString(
            R.string.result_template,
            roundedKrs,
            roundedKrg,
            roundedHS,
            roundedCS,
            roundedSS,
            roundedNS,
            roundedOS,
            roundedAS,
            roundedHG,
            roundedCG,
            roundedSG,
            roundedNG,
            roundedOG,
            roundedQrp,
            roundedQch,
            roundedQgh
        )

        binding.resultTextView.text = resultText
    }

}
