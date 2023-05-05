package com.example.mynutrition

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.mynutrition.databinding.ActivityNutritionDetailsBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class NutritionDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNutritionDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val protein=intent.getDoubleExtra("protein",0.0)
        val carbs=intent.getDoubleExtra("carbs",0.0)
        val tFats=intent.getDoubleExtra("totalFat",0.0)
        val calories=intent.getDoubleExtra("calories",0.0)
        val name=intent.getStringExtra("name")
        val sugar=intent.getDoubleExtra("sugar",0.0)
        val sFats=intent.getDoubleExtra("saturatedFat",0.0)
        val sodium=intent.getIntExtra("sodium",0)
        val potassium=intent.getIntExtra("potassium",0)
        val cholesterol=intent.getIntExtra("cholesterol",0)
        val fiber=intent.getDoubleExtra("fiber",0.0)
        val serving=intent.getDoubleExtra("servingSize",0.0)
        binding=ActivityNutritionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val total=protein+carbs+tFats
        val carbsPercentage=(carbs/total)*100
        val proteinPercentage=(protein/total)*100
        val fatsPercentage=(tFats/total)*100

        binding.apply {
            binding.name.text=name
            backButton.setOnClickListener{
                finish()
            }
            binding.serving.text="$serving g serving"
            binding.calories.text="$calories cal"
            binding.tFat.text="$tFats g"
            binding.carbs.text="$carbs g"
            binding.sugar.text="$sugar g"
            binding.fiber.text="$fiber g"
            binding.sFat.text="$sFats g"
            binding.protein.text="$protein g"
            binding.sodium.text="$sodium mg"
            binding.potassium.text="$potassium mg"
            binding.cholestrol.text="$cholesterol mg"

            pieChart.setUsePercentValues(true)
            pieChart.description.isEnabled = true
            pieChart.description.text="Macros ratio"
            pieChart.description.textSize= 10F

            pieChart.setExtraOffsets(5f, 5f, 5f, 5f)

            // on below line we are setting drag for our pie chart
            pieChart.dragDecelerationFrictionCoef = 0.95f

            // on below line we are setting hole
            // and hole color for pie chart
            pieChart.isDrawHoleEnabled = true
            pieChart.setHoleColor(Color.WHITE)

            // on below line we are setting circle color and alpha
            pieChart.setTransparentCircleColor(Color.WHITE)
            pieChart.setTransparentCircleAlpha(110)

            // on  below line we are setting hole radius
            pieChart.holeRadius = 58f
            pieChart.transparentCircleRadius = 61f

            // on below line we are setting center text
            pieChart.setDrawCenterText(true)

            // on below line we are setting
            // rotation for our pie chart
            pieChart.rotationAngle = 0f

            // enable rotation of the pieChart by touch
            pieChart.isRotationEnabled = true
            pieChart.isHighlightPerTapEnabled = true

            pieChart.centerText="$calories cal"

            // on below line we are setting animation for our pie chart
            pieChart.animateY(1400, Easing.EaseInOutQuad)

            // on below line we are disabling our legend for pie chart
            pieChart.legend.isEnabled = false
            pieChart.setEntryLabelColor(Color.BLACK)
            pieChart.setEntryLabelTextSize(12f)

            // on below line we are creating array list and
            // adding data to it to display in pie chart
            val entries: ArrayList<PieEntry> = ArrayList()
            if(protein!=0.0){
                entries.add(PieEntry(proteinPercentage.toFloat(),"Protein"))
            }
            if(carbs!=0.0){
                entries.add(PieEntry(carbsPercentage.toFloat(),"carbs"))
            }
            if(tFats!=0.0){
                entries.add(PieEntry(fatsPercentage.toFloat(),"fats"))
            }

            // on below line we are setting pie data set
            val dataSet = PieDataSet(entries, "")

            // on below line we are setting icons.
            dataSet.setDrawIcons(false)

            // on below line we are setting slice for pie
            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            // add a lot of colors to list
            val colors: ArrayList<Int> = ArrayList()
            colors.add(ContextCompat.getColor(this@NutritionDetailsActivity,R.color.purple_200))
            colors.add(ContextCompat.getColor(this@NutritionDetailsActivity,R.color.yellow))
            colors.add(ContextCompat.getColor(this@NutritionDetailsActivity,R.color.red))


            // on below line we are setting colors.
            dataSet.colors = colors
            dataSet.xValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
            // on below line we are setting pie data set
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.BLACK)
            pieChart.data = data

            // undo all highlights
            pieChart.highlightValues(null)

            // loading chart
            pieChart.invalidate()
        }
    }
}