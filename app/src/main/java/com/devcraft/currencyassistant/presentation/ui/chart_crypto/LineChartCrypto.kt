package com.devcraft.currencyassistant.presentation.ui.chart_crypto

import android.content.Context
import androidx.core.content.ContextCompat
import com.devcraft.currencyassistant.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class LineChartCrypto(private val lineChart: LineChart, private val ctx: Context) {
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData


    fun initLineDataSet(data: ArrayList<Entry>) {
        lineDataSet = LineDataSet(data, " ")
        lineDataSet.form = Legend.LegendForm.NONE
        lineDataSet.color = ContextCompat.getColor(ctx, R.color.orange_color)
        lineDataSet.fillColor = ContextCompat.getColor(ctx, R.color.white)
        lineDataSet.lineWidth = 1f
        lineDataSet.valueTextSize = 13f
        lineDataSet.circleRadius = 1f
        lineDataSet.setCircleColor(ContextCompat.getColor(ctx, R.color.orange_color))
        lineDataSet.circleHoleColor = ContextCompat.getColor(ctx, R.color.orange_color)
        lineDataSet.circleRadius = 1.5f
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawFilled(true)

    }

    fun setLineData() {
        lineData = LineData(lineDataSet)
        lineData.setDrawValues(false)
        lineData.setDrawValues(false)
    }

    fun initLineChart() {
        lineChart.data = lineData
        lineChart.axisLeft.isEnabled = false
        lineChart.highlightValue(null)
        lineChart.xAxis.textColor = ContextCompat.getColor(ctx, R.color.black)
        lineChart.axisRight.textColor = ContextCompat.getColor(ctx, R.color.black)
        lineChart.description = null
        lineChart.legend.isEnabled = true
        lineChart.axisRight.gridLineWidth = 1f
        lineChart.xAxis.gridLineWidth = 1f
        lineChart.xAxis.gridColor = ContextCompat.getColor(ctx, R.color.bg_main_color)
        lineChart.axisRight.gridColor = ContextCompat.getColor(ctx, R.color.orange_2)
        lineChart.xAxis.axisLineColor = ContextCompat.getColor(ctx, R.color.bg_main_color)
        lineChart.axisRight.axisLineColor = ContextCompat.getColor(ctx, R.color.bg_main_color)
        lineChart.axisRight.setDrawTopYLabelEntry(true)
        lineChart.setTouchEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.legend.textColor = ContextCompat.getColor(ctx, R.color.black)
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.isEnabled = false
        /*val formatter: ValueFormatter = object : ValueFormatter() {
            @SuppressLint("SimpleDateFormat")
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                val c = Calendar.getInstance()
                c.timeInMillis = value.toLong()
                val format = SimpleDateFormat(typeDate, Locale.US)
                return format.format(c.time)
            }
        }*/
    }

}