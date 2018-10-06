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
    private fun isOp(str: String): Boolean = (str.last() == '/' || str.last() == '*' || str.last() == '-' || str.last() == '+')

    private fun vibration() {
        val mills = 10L
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mills)
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
            if (str != null &&  str != "" && str.last().isLetter()) tvOut?.text = ""
            else
                tvOut?.text = if (str != null && str.isNotEmpty()) str.substring(0, str.length - 1) else ""
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
            R.id.factButton -> addText(str, "!")
            R.id.sinButton -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "sin(")
                else addText(str, "sin(")
            }
            R.id.cosButton -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "cos(")
                else addText(str, "cos(")
            }
            R.id.tanButton -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "tan(")
                else addText(str, "tan(")
            }
            R.id.logButton -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "log(")
                else addText(str, "log(")
            }
            R.id.log2Button -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "log2(")
                else addText(str, "log2(")
            }
            R.id.log10Button -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "log10(")
                else addText(str, "log10(")
            }
            R.id.sqrtButton -> {
                lvlBracket++
                if (wasEx || wasEq || str == "") addText("", "√(")
                else addText(str, "√(")
            }
            R.id.leftBracketButton -> {
                lvlBracket++
                if (wasEq) addText("", "(") else addText(str, "(")
            }
            R.id.rightBracketButton ->  {
                lvlBracket--
                if (wasEq) addText("", ")") else addText(str, ")")
            }
            R.id.oneButton ->  if (wasEq) addText("", "1") else addText(str, "1")
            R.id.twoButton -> if (wasEq) addText("", "2") else addText(str, "2")
            R.id.thereButton -> if (wasEq) addText("", "3") else addText(str, "3")
            R.id.fourButton -> if (wasEq) addText("", "4") else addText(str, "4")
            R.id.fiveButton -> if (wasEq) addText("", "5") else addText(str, "5")
            R.id.sixButton -> if (wasEq) addText("", "6") else addText(str, "6")
            R.id.sevenButton -> if (wasEq) addText("", "7") else addText(str, "7")
            R.id.eightButton -> if (wasEq) addText("", "8") else addText(str, "8")
            R.id.nineButton -> if (wasEq) addText("", "9") else addText(str, "9")
            R.id.zeroButton -> if (wasEq) addText("", "0") else addText(str, "0")
            R.id.piButton -> if (wasEq) addText("", "pi") else addText(str, "pi")
            R.id.eButton -> if (wasEq) addText("", "e") else addText(str, "e")
            R.id.dotButton -> if (str != null && str.isNotEmpty() && str.last() == '.' ) str else addText(str, ".")
            R.id.expButton -> {
                if (str != null && wasNeg) {
                    wasNeg = false
                    addText(str.substring(0, str.length - 2), "^")
                }
                else if (str != null && str != "" &&  isOp(str)) addText(str.substring(0, str.length - 1), "^")
                else if (wasEx || str == "") ""
                else {
                    if (str != null) wasNeg = isOp(str)
                    addText(str, "^")
                }
            }
            R.id.divButton -> {
                if (str != null && wasNeg) {
                    wasNeg = false
                    addText(str.substring(0, str.length - 2), "/")
                }
                else if (str != null && str != "" && isOp(str)) addText(str.substring(0, str.length - 1), "/")
                else if (wasEx || str == "") ""
                else addText(str, "/")
            }
            R.id.mulButton -> {
                if (str != null && wasNeg) {
                    wasNeg = false
                    addText(str.substring(0, str.length - 2), "*")
                }
                else if (str != null && str != "" &&  isOp(str)) addText(str.substring(0, str.length - 1), "*")
                else if (wasEx || str == "") ""
                else {
                    if (str != null) wasNeg = isOp(str)
                    addText(str, "*")
                }

            }
            R.id.plusButton -> {
                if (str != null && wasNeg) {
                    wasNeg = false
                    str.substring(0, str.length - 1)
                }
                else if (str != null && str != "" &&  isOp(str)) addText(str.substring(0, str.length - 1), "+")
                else if (wasEx || str == "") ""
                else {
                    if (str != null) wasNeg = isOp(str)
                    addText(str, "+")
                }
            }
            R.id.subButton -> {
                if (str != null && str != "" &&  (str.last() == '+' || str.last() == '-')) addText(str.substring(0, str.length - 1), "-")
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
        super.onSaveInstanceState(outState)
        outState.apply {
            putString("exp", tvOut?.text.toString())
            putBoolean("Eq", wasEq)
            putBoolean("Ex", wasEx)
            putBoolean("Neg", wasNeg)
            putInt("lvl", lvlBracket)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tvOut?.text =  savedInstanceState.getString("exp")
        wasEq = savedInstanceState.getBoolean("Eq")
        wasEx = savedInstanceState.getBoolean("Ex")
        wasNeg = savedInstanceState.getBoolean("Neg")
        lvlBracket = savedInstanceState.getInt("lvl")
    }

}
