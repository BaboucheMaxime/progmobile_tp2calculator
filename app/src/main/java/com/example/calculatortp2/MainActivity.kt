package com.example.calculatortp2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val button0 : Button = findViewById(R.id.button0)
    val button1 : Button = findViewById(R.id.button1)
    val button2 : Button = findViewById(R.id.button2)
    val button3 : Button = findViewById(R.id.button3)
    val button4 : Button = findViewById(R.id.button4)
    val button5 : Button = findViewById(R.id.button5)
    val button6 : Button = findViewById(R.id.button6)
    val button7 : Button = findViewById(R.id.button7)
    val button8 : Button = findViewById(R.id.button8)
    val button9 : Button = findViewById(R.id.button9)
    val button_mul : Button = findViewById(R.id.buttonmul)
    val button_div : Button = findViewById(R.id.buttondiv)
    val button_add : Button = findViewById(R.id.buttonadd)
    val button_minus : Button = findViewById(R.id.buttonminus)
    val button_dot : Button = findViewById(R.id.buttondot)
    val button_reset : Button = findViewById(R.id.buttonreset)
    val button_equal : Button = findViewById(R.id.buttonequal)
    val field_result : TextView = findViewById(R.id.tv_result)
    var expression : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener(evaluateExpression("0", true))
        button1.setOnClickListener(evaluateExpression("1", true))
        button2.setOnClickListener(evaluateExpression("2", true))
        button3.setOnClickListener(evaluateExpression("3", true))
        button4.setOnClickListener(evaluateExpression("4", true))
        button5.setOnClickListener(evaluateExpression("5", true))
        button6.setOnClickListener(evaluateExpression("6", true))
        button7.setOnClickListener(evaluateExpression("7", true))
        button8.setOnClickListener(evaluateExpression("8", true))
        button9.setOnClickListener(evaluateExpression("9", true))

        button_add.setOnClickListener {
            evaluateExpression("+", clear = true)
        }
        button_minus.setOnClickListener {
            evaluateExpression("-", clear = true)
        }
        button_mul.setOnClickListener {
            evaluateExpression("*", clear = true)
        }
        button_div.setOnClickListener {
            evaluateExpression("/", clear = true)
        }
        button_dot.setOnClickListener {
            evaluateExpression(".", clear = true)
        }
        button_reset.setOnClickListener {
            field_result.text = ""
        }

        /* function for computation */
        button_equal.setOnClickListener {
            val text = tvExpression.text.toString()
            val expression = ExpressionBuilder(text).build()

            val result = expression.evaluate()
            val longResult = result.toLong()
            if (result == longResult.toDouble()) {
                tvResult.text = longResult.toString()
            } else {
                tvResult.text = result.toString()
            }
        }
    }

    fun evaluateExpression(string: String, clear: Boolean): View.OnClickListener? {
        if(clear) {
            Result.text = ""
            Expression.append(string)
        } else {
            Expression.append(Result.text)
            Expression.append(string)
            Result.text = ""
        }
    }
}