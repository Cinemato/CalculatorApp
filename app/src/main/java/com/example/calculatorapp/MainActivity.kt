package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.calculatorapp.databinding.ActivityMainBinding
// import android.util.Log
import android.view.ViewGroup
import com.ezylang.evalex.*
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            for (i in 0 until layoutBtn.childCount) {
                val outerLayout: View = layoutBtn.getChildAt(i)
                if (outerLayout is ViewGroup) {
                    for (j in 0 until outerLayout.childCount) {
                        val button: View = outerLayout.getChildAt(j)
                        if (button is Button) {
                            button.setOnClickListener {
                                applyButtonOperation(button)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showButtonText(button: Button) {
        binding.tvNum.text = binding.tvNum.text.toString() + button.text.toString()
    }

    private fun calculateAndSetResult(equation: String) {
        try {
            val result = Expression(equation).evaluate()

            if(result.stringValue.contains(".")) {
                val decimalResult: BigDecimal = result.numberValue.setScale(5, RoundingMode.HALF_EVEN)
                binding.tvResult.text = decimalResult.toString()
            }
            else {
                binding.tvResult.text = result.stringValue
            }
        } catch (e: Exception) {
            binding.tvResult.text = "Invalid Input"
        }
    }

    private fun applyButtonOperation(button: Button) {
        binding.apply {
            when(button.text) {
                "AC" -> tvNum.text = ""
                "⌫" -> tvNum.text = tvNum.text.dropLast(1)
                "=" -> calculateAndSetResult(tvNum.text.toString())
                else -> {
                    showButtonText(button)
                }
            }
        }
    }
}