package com.example.calculatortp2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val expression : StringBuilder = StringBuilder("")

        button0.setOnClickListener{addArgumentToExpression("0", expression, field_result)}
        button1.setOnClickListener{addArgumentToExpression("1", expression, field_result)}
        button2.setOnClickListener{addArgumentToExpression("2", expression, field_result)}
        button3.setOnClickListener{addArgumentToExpression("3", expression, field_result)}
        button4.setOnClickListener{addArgumentToExpression("4", expression, field_result)}
        button5.setOnClickListener{addArgumentToExpression("5", expression, field_result)}
        button6.setOnClickListener{addArgumentToExpression("6", expression, field_result)}
        button7.setOnClickListener{addArgumentToExpression("7", expression, field_result)}
        button8.setOnClickListener{addArgumentToExpression("8", expression, field_result)}
        button9.setOnClickListener{addArgumentToExpression("9", expression, field_result)}

        button_add.setOnClickListener {
            addArgumentToExpression("+", expression, field_result)
        }
        button_minus.setOnClickListener {
            addArgumentToExpression("-", expression, field_result)
        }
        button_mul.setOnClickListener {
            addArgumentToExpression("*", expression, field_result)
        }
        button_div.setOnClickListener {
            addArgumentToExpression("/", expression, field_result)
        }
        button_dot.setOnClickListener {
            addArgumentToExpression(".", expression, field_result)
        }
        button_reset.setOnClickListener {
            expression.clear()
            field_result.text = "0"
        }

        /* function for running computation */
        button_equal.setOnClickListener {
            var result = eval(expression.toString()).toString()
            field_result.text = result
            expression.clear()
            expression.append(result)
        }
    }

    /* adds an operator or a figure to the expression depending on the button pressed */
    fun addArgumentToExpression(string: String, expression:StringBuilder, field_result:TextView) {

        expression.append(string)
        field_result.text = expression.toString()
    }

    /* evaluates the string expression as a math expression */
    private fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm()
                    else if (eat('-'.toInt())) x -= parseTerm()
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor()
                    else if (eat('/'.toInt())) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor()
                if (eat('-'.toInt())) return -parseFactor()
                var x: Double
                val startPos = pos
                if (eat('('.toInt())) {
                    x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {
                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    x = parseFactor()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.toInt())) x = Math.pow(x, parseFactor())
                return x
            }
        }.parse()
    }
}