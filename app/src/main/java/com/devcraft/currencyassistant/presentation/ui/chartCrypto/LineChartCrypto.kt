package com.devcraft.currencyassistant.presentation.ui.chartCrypto

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class LineChartCrypto(private val lineChart: LineChart) {
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData


    fun initLineDataSet(data: ArrayList<Entry>) {
        lineDataSet = LineDataSet(data, "Days")
        lineDataSet.form = Legend.LegendForm.CIRCLE
        lineDataSet.color = parseColor("#4173E6")
        lineDataSet.fillColor = parseColor("#3D415E")
        lineDataSet.lineWidth = 3f
        lineDataSet.valueTextSize = 13f
        lineDataSet.circleRadius = 1.5f
        lineDataSet.setCircleColor(parseColor("#4173E6"))
        lineDataSet.circleHoleColor = parseColor("#4173E6")
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
        lineChart.xAxis.textColor = parseColor("#FFFFFF")
        lineChart.axisRight.textColor = parseColor("#FFFFFF")
        lineChart.description = null
        lineChart.legend.isEnabled = true
        lineChart.axisRight.gridLineWidth = 1f
        lineChart.xAxis.gridLineWidth = 1f
        lineChart.xAxis.gridColor = parseColor("#3D415E")
        lineChart.axisRight.gridColor = parseColor("#3D415E")
        lineChart.xAxis.axisLineColor = parseColor("#3D415E")
        lineChart.axisRight.setDrawTopYLabelEntry(true)
        lineChart.setTouchEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.legend.textColor = parseColor("#3D415E")


        val formatter: ValueFormatter = object : ValueFormatter() {
            @SuppressLint("SimpleDateFormat")
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                val c = Calendar.getInstance()
                c.timeInMillis = value.toLong()
                val format = SimpleDateFormat("d", Locale.US)
                return format.format(c.time)
            }
        }
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.valueFormatter = formatter
    }

}