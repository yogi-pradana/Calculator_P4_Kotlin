package com.p3b1yogi.calculatorp4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.p3b1yogi.calculatorp4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var currentInput: String = ""
    private var lastNumber: Double? = null
    private var operator: String? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                appendToInput(button.text.toString())
            }
        }

        // Operator buttons
        binding.btnPlus.setOnClickListener { setOperator("+") }
        binding.btnMin.setOnClickListener { setOperator("-") }
        binding.btnMultiply.setOnClickListener { setOperator("*") }
        binding.btnDivide.setOnClickListener { setOperator("/") }
        binding.btnPercent.setOnClickListener { percent() }

        // Equal button
        binding.btnSama.setOnClickListener { calculateResult() }

        // AC button
        binding.btnAc.setOnClickListener { reset() }

        // Decimal point button
        binding.btnDot.setOnClickListener { appendToInput(".") }
    }

    // Append number or dot to the input
    private fun appendToInput(value: String) {
        currentInput += value
        binding.displayInput.text = currentInput
    }

    // Set the operator (+, -, *, /)
    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty() && operator == null) {
            lastNumber = currentInput.toDoubleOrNull()
            operator = op
            currentInput += " $operator "
            binding.displayInput.text = currentInput
        }
    }

    // operasi persen
    private fun percent() {
        if (currentInput.isNotEmpty()) {
            currentInput = (currentInput.toDouble() / 100).toString()
            binding.displayInput.text = currentInput
        }
    }

    // Perform calculation and show the result
    private fun calculateResult() {
        val expressionParts = currentInput.split(" ")
        if (expressionParts.size == 3) {
            val number1 = expressionParts[0].toDoubleOrNull()
            val operator = expressionParts[1]
            val number2 = expressionParts[2].toDoubleOrNull()

            if (number1 != null && number2 != null) {
                val result = when (operator) {
                    "+" -> number1 + number2
                    "-" -> number1 - number2
                    "*" -> number1 * number2
                    "/" -> if (number2 != 0.0) number1 / number2 else "Error"
                    else -> ""
                }
                binding.displayResult.text = result.toString()
                currentInput = result.toString()
            }
        }
    }

    // Reset  calculator
    private fun reset() {
        currentInput = ""
        lastNumber = null
        operator = null
        binding.displayInput.text = "0"
        binding.displayResult.text = "" 
    }
}