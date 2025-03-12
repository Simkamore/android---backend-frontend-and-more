package com.example.myap

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<TextView>(R.id.btn_0).setOnClickListener {setTextFields("0")}
        findViewById<TextView>(R.id.btn_1).setOnClickListener {setTextFields("1")}
        findViewById<TextView>(R.id.btn_2).setOnClickListener {setTextFields("2")}
        findViewById<TextView>(R.id.btn_3).setOnClickListener {setTextFields("3")}
        findViewById<TextView>(R.id.btn_4).setOnClickListener {setTextFields("4")}
        findViewById<TextView>(R.id.btn_5).setOnClickListener {setTextFields("5")}
        findViewById<TextView>(R.id.btn_6).setOnClickListener {setTextFields("6")}
        findViewById<TextView>(R.id.btn_7).setOnClickListener {setTextFields("7")}
        findViewById<TextView>(R.id.btn_8).setOnClickListener {setTextFields("8")}
        findViewById<TextView>(R.id.btn_9).setOnClickListener {setTextFields("9")}
        findViewById<TextView>(R.id.btn_minus).setOnClickListener{setTextFields("-")}
        findViewById<TextView>(R.id.btn_multiply).setOnClickListener{setTextFields("*")}
        findViewById<TextView>(R.id.btn_divide).setOnClickListener{setTextFields("/")}
        findViewById<TextView>(R.id.btn_plus).setOnClickListener{setTextFields("+")}

        findViewById<TextView>(R.id.btn_C).setOnClickListener{
            findViewById<TextView>(R.id.math_operation).text = ""
            findViewById<TextView>(R.id.result_text).text = ""
        }
        findViewById<TextView>(R.id.btn_equal).setOnClickListener{
            try {

                val ex = ExpressionBuilder(findViewById<TextView>(R.id.math_operation).text.toString()).build()
                val result = ex.evaluate()

                val longRes = result.toLong()
                if (result == longRes.toDouble())
                    findViewById<TextView>(R.id.result_text).text = longRes.toString()
                else
                    findViewById<TextView>(R.id.result_text).text = result.toString()
            } catch (e:Exception) {
                Log.d("Ошибка","сообщение: ${e.message}")
            }
        }
    }
    private fun setTextFields(str:String) {
        if (findViewById<TextView>(R.id.result_text).text != "") {
            findViewById<TextView>(R.id.math_operation).text = findViewById<TextView>(R.id.result_text).text
            findViewById<TextView>(R.id.result_text).text = ""
        }
        findViewById<TextView>(R.id.math_operation).append(str)
    }
}