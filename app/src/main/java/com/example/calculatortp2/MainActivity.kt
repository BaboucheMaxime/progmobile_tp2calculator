package com.example.calculatortp2

import android.os.Bundle
import android.widget.Button
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
    val expression : StringBuilder = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener{addArgumenttoExpression("0")}
        button1.setOnClickListener{addArgumenttoExpression("1")}
        button2.setOnClickListener{addArgumenttoExpression("2")}
        button3.setOnClickListener{addArgumenttoExpression("3")}
        button4.setOnClickListener{addArgumenttoExpression("4")}
        button5.setOnClickListener{addArgumenttoExpression("5")}
        button6.setOnClickListener{addArgumenttoExpression("6")}
        button7.setOnClickListener{addArgumenttoExpression("7")}
        button8.setOnClickListener{addArgumenttoExpression("8")}
        button9.setOnClickListener{addArgumenttoExpression("9")}

        button_add.setOnClickListener {
            addArgumenttoExpression("+")
        }
        button_minus.setOnClickListener {
            addArgumenttoExpression("-")
        }
        button_mul.setOnClickListener {
            addArgumenttoExpression("*")
        }
        button_div.setOnClickListener {
            addArgumenttoExpression("/")
        }
        button_dot.setOnClickListener {
            addArgumenttoExpression(".")
        }
        button_reset.setOnClickListener {
            expression.setLength(0)
            field_result.text = "0"
        }

        /* function for computation */
        button_equal.setOnClickListener {
            var result = eval(expression.toString()).toString()
            field_result.text = result

        }
    }

    fun addArgumenttoExpression(string: String) {
        expression.append(string)
    }

    fun eval(str: String): Double {
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
                    if (eat('+'.toInt())) x += parseTerm() // addition
                    else if (eat('-'.toInt())) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor() // multiplication
                    else if (eat('/'.toInt())) x /= parseFactor() // division
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor() // unary plus
                if (eat('-'.toInt())) return -parseFactor() // unary minus
                var x: Double
                val startPos = pos
                if (eat('('.toInt())) { // parentheses
                    x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) { // numbers
                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) { // functions
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    val func = str.substring(startPos, pos)
                    x = parseFactor()
                    x =
                        if (func == "sqrt") Math.sqrt(x) else if (func == "sin") Math.sin(
                            Math.toRadians(
                                x
                            )
                        ) else if (func == "cos") Math.cos(
                            Math.toRadians(x)
                        ) else if (func == "tan") Math.tan(Math.toRadians(x)) else throw RuntimeException(
                            "Unknown function: $func"
                        )
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.toInt())) x = Math.pow(x, parseFactor()) // exponentiation
                return x
            }
        }.parse()
    }
}