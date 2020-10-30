package com.example.workoutapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)
        val toolbar = toolbar_activity_bmi
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Calculate BMI"
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        btn_activity_bmi_calculate.setOnClickListener {
            if (validateMetricUnits()) {
                calculateBMI()
            } else {
                Toast.makeText(this, "Fill the fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (etMetricUnitHeight.text.toString().isEmpty() || etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun calculateBMI() {
        linear_layout_show_bmi_metrics.visibility = View.VISIBLE
        val weightValue = etMetricUnitWeight.text.toString().toFloat()
        val heightValue = etMetricUnitHeight.text.toString().toFloat()
        val result = (weightValue / heightValue / heightValue) * 10000
        val bmiResult = BigDecimal(result.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        if (result < 18.5) {
            tv_activity_bmi_status.text = "Underweight"
            tv_activity_bmi_message.text = "Not great! Work Hard"
        } else if (result >= 18.5 && result <= 24.9) {
            tv_activity_bmi_status.text = "Normal"
            tv_activity_bmi_message.text = "Great!! Nice work!"
        } else if (result >= 25 && result <= 29.9) {
            tv_activity_bmi_status.text = "Overweight"
            tv_activity_bmi_message.text = "Not great! Work Hard"
        } else {
            tv_activity_bmi_status.text = "No dimension :D"
            tv_activity_bmi_message.text = "Very bad :("
        }
        tv_your_bmi.text = bmiResult
    }
}