package com.example.dmitry.calculator

import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator


var factorial: Operator = object : Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

    override fun apply(vararg args: Double): Double {
        val arg = args[0].toInt()
        if (arg.toDouble() != args[0]) {
            throw IllegalArgumentException("Operand for factorial has to be an integer")
        }
        if (arg < 0) {
            throw IllegalArgumentException("The operand of the factorial can not be less than zero")
        }
        var result = 1.0
        for (i in 1..arg) {
            result *= i.toDouble()
        }
        return result
    }
}

class Parser {

    private fun isWhole(value: Double):Boolean { return value - value.toInt() == 0.0 }

    fun evaluate(str2: String?): String {

        val str: String? = str2?.replace("√", "sqrt")

        return try {
            val op = ExpressionBuilder(str).operator(factorial).build()

            try {
                val res: Double = op.evaluate()
                return if (!isWhole(res)) res.toString() else (res.toLong()).toString()
            } catch (var4: Exception) {
                "Error"
            }

        } catch (var5: Exception) {
            "Error"
        }
    }
}