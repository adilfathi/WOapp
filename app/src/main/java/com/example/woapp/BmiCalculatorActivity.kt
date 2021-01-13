package com.example.woapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi_calculator.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiCalculatorActivity : AppCompatActivity() {

    private val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    private val US_UNIT_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)

        setSupportActionBar(toolbar_bmi)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btn_calculate.setOnClickListener {
            if (currentVisibleView == METRIC_UNIT_VIEW) {
                if (validateMetricUnit()) {
                    val heightValue: Float = edt_height.text.toString().toFloat() / 100
                    val weightValue: Float = edt_weight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBmiResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter the valid Value!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (validateUsUnit()) {
                    val usHeightFeetValue: String = edt_us_heightFeet.text.toString()
                    val usHeightInchValue: String = edt_us_heightInch.text.toString()
                    val usWeightValue: Float = edt_us_weight.text.toString().toFloat()
                    val heightValue = usHeightFeetValue.toFloat() + usHeightInchValue.toFloat() * 12

                    val bmi = 703 * (usWeightValue / (heightValue * heightValue))
                    displayBmiResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter the valid Value!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitView()
            } else {
                makeVisibleUsUnitView()
            }
        }
    }

    private fun makeVisibleMetricUnitView() {
        currentVisibleView = METRIC_UNIT_VIEW
        til_weight.visibility = View.VISIBLE
        til_height.visibility = View.VISIBLE

        edt_weight.text!!.clear()
        edt_height.text!!.clear()

        til_us_weight.visibility = View.GONE
        linearUsHeight.visibility = View.GONE

        linearMetric.visibility = View.VISIBLE
        linearResult.visibility = View.GONE
    }

    private fun makeVisibleUsUnitView() {
        currentVisibleView = US_UNIT_VIEW
        til_weight.visibility = View.GONE
        til_height.visibility = View.GONE

        edt_us_weight.text!!.clear()
        edt_us_heightFeet.text!!.clear()
        edt_us_heightInch.text!!.clear()

        til_us_weight.visibility = View.VISIBLE
        linearUsHeight.visibility = View.VISIBLE

        linearUS.visibility = View.VISIBLE
        linearResult.visibility = View.GONE
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very Underweight"
            bmiDescription = "You are too Skinny, You need to Eat more."
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely Underweight"
            bmiDescription = "You are severely Skinny, You need to Eat more."
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "You are Skinny, You need to Eat more."
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Good Shape"
            bmiDescription = "Nice you are in a Good Shape, Keep it up!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "You are Fat, You need to Diet."
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Level 1 (Severely Obese)"
            bmiDescription = "Oh no you are in Obese level, You need to Diet and do Workout"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Level 2 (Very Obese)"
            bmiDescription = "You are in a Dangerous Condition, Go meet a Doctor!"
        } else {
            bmiLabel = "Obese Level 3 (Dangerous Obese)"
            bmiDescription = "You are in a Dangerous Condition, Go meet a Doctor if you don't want to die."
        }

        linearResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        txtBmi_bmi.text = bmiValue
        txtBmi_type.text = bmiLabel
        txtBmi_description.text = bmiDescription
    }

    private fun validateMetricUnit(): Boolean {
        var isValid = true

        if (edt_weight.text.toString().isEmpty())
            isValid = false
        else if (edt_height.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun validateUsUnit(): Boolean {
        var isValid = true

        when {
            edt_us_heightFeet.text.toString().isEmpty() -> isValid = false
            edt_us_heightInch.text.toString().isEmpty() -> isValid = false
            edt_us_weight.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }
}