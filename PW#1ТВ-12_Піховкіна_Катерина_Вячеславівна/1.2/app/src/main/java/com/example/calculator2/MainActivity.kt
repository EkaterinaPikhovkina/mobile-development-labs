package com.example.calculator2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculate() }
    }

    private fun calculate() {
        val carbon = binding.inputCG.text.toString().toDoubleOrNull() ?: 0.0
        val hydrogen = binding.inputHG.text.toString().toDoubleOrNull() ?: 0.0
        val oxygen = binding.inputOG.text.toString().toDoubleOrNull() ?: 0.0
        val sulfur = binding.inputSG.text.toString().toDoubleOrNull() ?: 0.0
        val ash = binding.inputAG.text.toString().toDoubleOrNull() ?: 0.0
        val moisture = binding.inputWG.text.toString().toDoubleOrNull() ?: 0.0
        val vanadium = binding.inputVG.text.toString().toDoubleOrNull() ?: 0.0
        val qdaf = binding.inputQidaf.text.toString().toDoubleOrNull() ?: 0.0

        val cR = carbon * (100 - moisture - ash) / 100
        val hR = hydrogen * (100 - moisture - ash) / 100
        val oR = oxygen * (100 - (moisture / 10) - (ash / 10)) / 100
        val sR = sulfur * (100 - moisture - ash) / 100
        val aR = ash * (100 - moisture) / 100
        val vR = vanadium * (100 - moisture) / 100

        // Розрахунок нижчої теплоти згоряння
        val qR = qdaf * (100 - moisture - aR) / 100 - 0.025 * moisture

        // Округлення значень до двох знаків після коми
        val roundedCR = String.format("%.2f", cR)
        val roundedHR = String.format("%.2f", hR)
        val roundedSR = String.format("%.2f", sR)
        val roundedOR = String.format("%.2f", oR)
        val roundedAR = String.format("%.2f", aR)
        val roundedVR = String.format("%.2f", vR)
        val roundedQR = String.format("%.2f", qR)

        val resultText = getString(
            R.string.result_template,
            roundedHR,
            roundedCR,
            roundedSR,
            roundedOR,
            roundedVR,
            roundedAR,
            roundedQR
        )

        binding.resultTextView.text = resultText
    }
}
