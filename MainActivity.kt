package com.example.myap

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var mathOperationTextView: TextView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mathOperationTextView = findViewById(R.id.math_operation)
        resultTextView = findViewById(R.id.result_text)


        findViewById<TextView>(R.id.btn_0).setOnClickListener { setTextFields("0") }
        findViewById<TextView>(R.id.btn_1).setOnClickListener { setTextFields("1") }
        findViewById<TextView>(R.id.btn_2).setOnClickListener { setTextFields("2") }
        findViewById<TextView>(R.id.btn_3).setOnClickListener { setTextFields("3") }
        findViewById<TextView>(R.id.btn_4).setOnClickListener { setTextFields("4") }
        findViewById<TextView>(R.id.btn_5).setOnClickListener { setTextFields("5") }
        findViewById<TextView>(R.id.btn_6).setOnClickListener { setTextFields("6") }
        findViewById<TextView>(R.id.btn_7).setOnClickListener { setTextFields("7") }
        findViewById<TextView>(R.id.btn_8).setOnClickListener { setTextFields("8") }
        findViewById<TextView>(R.id.btn_9).setOnClickListener { setTextFields("9") }
        findViewById<TextView>(R.id.btn_minus).setOnClickListener { setTextFields("-") }
        findViewById<TextView>(R.id.btn_multiply).setOnClickListener { setTextFields("*") }
        findViewById<TextView>(R.id.btn_divide).setOnClickListener { setTextFields("/") }
        findViewById<TextView>(R.id.btn_plus).setOnClickListener { setTextFields("+") }


        findViewById<TextView>(R.id.btn_C).setOnClickListener {
            mathOperationTextView.text = ""
            resultTextView.text = ""
        }


        findViewById<TextView>(R.id.btn_equal).setOnClickListener {
            getEqual()
        }
    }

    private fun setTextFields(str: String) {
        if (str == "C") {
            mathOperationTextView.text = ""
            resultTextView.text = ""
        } else {
            mathOperationTextView.append(str)
        }
    }

    private fun getEqual() {
        try {
            val expressionText = mathOperationTextView.text.toString()


            if (expressionText.isNullOrEmpty()) {
                resultTextView.text = ""
                return
            }


            val result = evaluateExpression(expressionText)

            // Отображение результата
            resultTextView.text = formatResult(result)
        } catch (e: Exception) {
            Log.d("Ошибка", "Сообщение: ${e.message}")
            resultTextView.text = "Ошибка"
        }
    }

    private fun evaluateExpression(expression: String): Double {

        val trimmedExpression = expression.replace("\\s+".toRegex(), "")


        val tokens = tokenize(trimmedExpression)


        return calculate(tokens)
    }

    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        val operators = setOf('+', '-', '*', '/')
        var numberBuffer = StringBuilder()

        for (char in expression) {
            if (char.isDigit()) {
                numberBuffer.append(char)
            } else if (operators.contains(char)) {
                if (numberBuffer.isNotEmpty()) {
                    tokens.add(numberBuffer.toString())
                    numberBuffer.clear()
                }
                tokens.add(char.toString())
            } else {
                throw IllegalArgumentException(": $char")
            }
        }

        if (numberBuffer.isNotEmpty()) {
            tokens.add(numberBuffer.toString())
        }

        return tokens
    }

    private fun calculate(tokens: List<String>): Double {
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> {
                    numbers.add(token.toDouble())
                }
                token.length == 1 && "+-*/".contains(token) -> {
                    val operator = token[0]
                    while (operators.isNotEmpty() && hasPrecedence(operator, operators.last())) {
                        val op = operators.removeAt(operators.size - 1)
                        val b = numbers.removeAt(numbers.size - 1)
                        val a = numbers.removeAt(numbers.size - 1)
                        numbers.add(applyOperator(a, b, op))
                    }
                    operators.add(operator)
                }
                else -> throw IllegalArgumentException(": $token")
            }
        }

        while (operators.isNotEmpty()) {
            val op = operators.removeAt(operators.size - 1)
            val b = numbers.removeAt(numbers.size - 1)
            val a = numbers.removeAt(numbers.size - 1)
            numbers.add(applyOperator(a, b, op))
        }

        return numbers[0]
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        return when (op1) {
            '*', '/' -> op2 == '*' || op2 == '/'
            else -> false
        }
    }

    private fun applyOperator(a: Double, b: Double, operator: Char): Double {
        return when (operator) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw ArithmeticException("")
                a / b
            }
            else -> throw IllegalArgumentException(": $operator")
        }
    }

    private fun formatResult(result: Double): String {

        return if (result == result.toInt().toDouble()) {
            result.toInt().toString()
        } else {

            String.format("%.2f", result)
        }
    }
}
