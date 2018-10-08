package com.example.dmitry.calculator

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.dmitry.myfirstproject.R
import android.os.Vibrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var tvOut: TextView? = null
    private val Parser = Parser()

    private var wasEq: Boolean = false
    private var wasEx: Boolean = false
    private var wasNeg: Boolean = false
    private var lvlBracket: Int = 0

    private fun addText(str: String?, x: String?): String = if (str != null && str != "") str + x else x.toString()
    private fun isOp(str: String): Boolean = (str.last() == '/' || str.last() == '*' || str.last() == '-' || str.last() == '+' || str.last() == '^')

    private fun vibration() {
        val mills = 10L
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (vibrator != null) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOut = findViewById(R.id.editText)

        val oclBtnEqual = View.OnClickListener {
            vibration()
            wasNeg = false
            var str: String? = tvOut?.text.toString()
            for (i in 1..lvlBracket) {
                str = addText(str, ")")
            }
            lvlBracket = 0
            val newStr = Parser.evaluate(str)
            if (newStr == "Error") {
                wasEq = true
                wasEx = true
            } else {
                wasEq = true
                wasEx = false
            }
            tvOut?.text = newStr
        }

        val oclBtnReset = View.OnClickListener {
            vibration()
            wasEq = false
            wasEx = false
            wasNeg = false
            tvOut?.text = ""
        }

        val oclBtnDel = View.OnClickListener {
            vibration()
            wasEq = false
            wasEx = false
            wasNeg = false
            val str: String? = tvOut?.text.toString()
            if (str != null && str in arrayOf("Infinity", "-Infinity", "Error", "NaN")) tvOut?.text = ""
            else if (str != null && str != "" && str.last() in arrayOf('n', 's', 'g')) tvOut?.text = str.substring(0, str.length - 3)
            else {
                if (str != "" && str?.last() == '(') lvlBracket--
                if (str != "" && str?.last() == ')') lvlBracket++
                tvOut?.text = if (str != null && str.isNotEmpty()) str.substring(0, str.length - 1) else ""
            }
        }

        oneButton.setOnClickListener(this)
        twoButton.setOnClickListener(this)
        thereButton.setOnClickListener(this)
        fourButton.setOnClickListener(this)
        fiveButton.setOnClickListener(this)
        sixButton.setOnClickListener(this)
        sevenButton.setOnClickListener(this)
        eightButton.setOnClickListener(this)
        nineButton.setOnClickListener(this)
        zeroButton.setOnClickListener(this)
        plusButton.setOnClickListener(this)
        subButton.setOnClickListener(this)
        mulButton.setOnClickListener(this)
        divButton.setOnClickListener(this)
        dotButton.setOnClickListener(this)
        leftBracketButton?.setOnClickListener(this)
        rightBracketButton?.setOnClickListener(this)
        sinButton?.setOnClickListener(this)
        cosButton?.setOnClickListener(this)
        tanButton?.setOnClickListener(this)
        logButton?.setOnClickListener(this)
        log2Button?.setOnClickListener(this)
        log10Button?.setOnClickListener(this)
        piButton?.setOnClickListener(this)
        eButton?.setOnClickListener(this)
        expButton?.setOnClickListener(this)
        sqrtButton?.setOnClickListener(this)
        factButton?.setOnClickListener(this)
        equalButton.setOnClickListener(oclBtnEqual)
        delButton.setOnClickListener(oclBtnDel)
        resetButton.setOnClickListener(oclBtnReset)
    }

    override fun onClick(v: View?) {
        val str: String? = tvOut?.text.toString()

        vibration()

        val newStr: String = when (v?.id) {
            R.id.factButton -> if (wasEx) addText("", "!") else addText(str, "!")
            R.id.sinButton -> addOperation(str, "sin(")
            R.id.cosButton -> addOperation(str, "cos(")
            R.id.tanButton -> addOperation(str, "tan(")
            R.id.logButton -> addOperation(str, "log(")
            R.id.log2Button -> addOperation(str, "log2(")
            R.id.log10Button -> addOperation(str, "log10(")
            R.id.sqrtButton -> addOperation(str, "√(")
            R.id.leftBracketButton -> {
                lvlBracket++
                if (wasEq) addText("", "(") else addText(str, "(")
            }
            R.id.rightBracketButton -> {
                lvlBracket--
                if (wasEq) addText("", ")") else addText(str, ")")
            }
            R.id.oneButton -> addDigit(str, "1")
            R.id.twoButton -> addDigit(str, "2")
            R.id.thereButton -> addDigit(str, "3")
            R.id.fourButton -> addDigit(str, "4")
            R.id.fiveButton -> addDigit(str, "5")
            R.id.sixButton -> addDigit(str, "6")
            R.id.sevenButton -> addDigit(str, "7")
            R.id.eightButton -> addDigit(str, "8")
            R.id.nineButton -> addDigit(str, "9")
            R.id.zeroButton -> addDigit(str, "0")
            R.id.piButton -> addDigit(str, "π")
            R.id.eButton -> addDigit(str, "e")
            R.id.dotButton -> {
                if (wasEq) addText("", ".")
                else if (str != null && str.isNotEmpty() && str.last() == '.') str else addText(str, ".")
            }
            R.id.expButton -> addOperation(str, "^", 2)
            R.id.divButton -> addOperation(str, "/", 2)
            R.id.mulButton -> addOperation(str, "*", 2)
            R.id.plusButton -> addOperation(str, "+", 1)
            R.id.subButton -> {
                if (str != null && str != "" && (str.last() == '+' || str.last() == '-')) addText(str.substring(0, str.length - 1), "-")
                else if (wasEx || str == "") ""
                else {
                    if (str != null) wasNeg = isOp(str)
                    addText(str, "-")
                }

            }
            else -> "null"
        }

        if (v?.id == R.id.oneButton || v?.id == R.id.twoButton || v?.id == R.id.thereButton || v?.id == R.id.fourButton
                || v?.id == R.id.fiveButton || v?.id == R.id.sixButton || v?.id == R.id.sevenButton || v?.id == R.id.eightButton
                || v?.id == R.id.nineButton || v?.id == R.id.zeroButton) {
            wasNeg = false
        }

        wasEq = false
        wasEx = false
        tvOut?.text = newStr
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putString("exp", tvOut?.text.toString())
            putBoolean("Eq", wasEq)
            putBoolean("Ex", wasEx)
            putBoolean("Neg", wasNeg)
            putInt("lvl", lvlBracket)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tvOut?.text = savedInstanceState.getString("exp")
        wasEq = savedInstanceState.getBoolean("Eq")
        wasEx = savedInstanceState.getBoolean("Ex")
        wasNeg = savedInstanceState.getBoolean("Neg")
        lvlBracket = savedInstanceState.getInt("lvl")
    }

    private fun addOperation(str: String?, char: String, len: Int): String {
        return if (str != null && wasNeg) {
            wasNeg = false
            addText(str.substring(0, str.length - len), if (char == "+") "" else char)
        } else if (str != null && str != "" && isOp(str)) addText(str.substring(0, str.length - 1), char)
        else if (wasEx || str == "") ""
        else {
            if (str != null) wasNeg = isOp(str)
            addText(str, char)
        }
    }

    private fun addOperation(str: String?, add: String?): String {
        lvlBracket++
        return if (wasEx || wasEq || str == "") addText("", add)
        else addText(str, add)
    }

    private fun addDigit(str: String?, add: String?): String {
        return if (wasEq) addText("", add) else addText(str, add)
    }
}