// MainActivity.kt
package com.example.gfgfut

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var textViewResult: TextView
    private var operand1: Double? = null
    private var pendingOperation: String = ""
    private val decimalFormat = DecimalFormat("#.######")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewResult = findViewById(R.id.textViewResult)
    }


    @SuppressLint("SetTextI18n")
    fun numberButtonClicked(view: View) {
        val button = view as Button
        val number = button.text.toString()


        if (textViewResult.text.toString() == "0" || textViewResult.text.toString() == "Error") {
            textViewResult.text = number
        } else {
            textViewResult.text = textViewResult.text.toString() + number
        }
    }


    @SuppressLint("SetTextI18n")
    fun decimalButtonClicked(view: View) {
        if (!textViewResult.text.contains(".")) {
            textViewResult.text = textViewResult.text.toString() + "."
        }
    }



    @SuppressLint("SetTextI18n")
    fun operationButtonClicked(view: View) {
        val button = view as Button
        val operation = button.text.toString()

        try {
            operand1 = textViewResult.text.toString().toDouble()
            pendingOperation = operation
            textViewResult.text = "0"
        } catch (e: NumberFormatException) {

            textViewResult.text = "Error"
        }
    }



    @SuppressLint("SetTextI18n")
    fun equalsButtonClicked(view: View) {
        try {
            val operand2 = textViewResult.text.toString().toDouble()

            var result: Double = 0.0

            when (pendingOperation) {
                "+" -> result = operand1!! + operand2
                "-" -> result = operand1!! - operand2
                "*" -> result = operand1!! * operand2
                "/" -> {
                    if (operand2 == 0.0) {
                        textViewResult.text = "Error"
                        return
                    }
                    result = operand1!! / operand2
                }
            }


            textViewResult.text = decimalFormat.format(result)


            operand1 = null
            pendingOperation = ""

        } catch (e: NumberFormatException) {
            textViewResult.text = "Error"
        } catch (e: NullPointerException) {
            "Error".also { textViewResult.text = it }
        }
    }



    fun clearButtonClicked(view: View) {
        textViewResult.text = "0"
        operand1 = null
        pendingOperation = ""
    }
}