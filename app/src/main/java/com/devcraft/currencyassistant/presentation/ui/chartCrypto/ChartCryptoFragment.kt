package com.devcraft.currencyassistant.presentation.ui.chartCrypto

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.devcraft.currencyassistant.BaseFragment
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.data.remote.dto.DataHistory
import com.devcraft.currencyassistant.databinding.FragmentChartCryptoBinding
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartCryptoFragment : BaseFragment<FragmentChartCryptoBinding>(FragmentChartCryptoBinding::inflate) {

    private val vm by viewModels<ChartViewModel>()
    private lateinit var lineList: ArrayList<Entry>
    private lateinit var chart: LineChartCrypto

    private val args: ChartCryptoFragmentArgs by navArgs()

    private lateinit var adapterFilter: ArrayAdapter<CharSequence>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun getData(filter: Int) {
        vm.getHistoryCrypto(args.dataChart?.id, filter)

        vm.historyLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                drawChart(it)
            }
        }
    }

    private fun initViews() {
        binding?.run {
            nameCurrency.text = args.dataChart?.name
            valueCurrency.text = "$" + String.format("%.2f", args.dataChart?.priceUsd)

            if (args.dataChart?.changePercent24Hr!! < 0.0) {
                percentChange.text = String.format("%.2f", args.dataChart?.changePercent24Hr) + "%"
                percentChange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_percent_down
                    )
                )
            } else {
                percentChange.text =
                    "+" + String.format("%.2f", args.dataChart?.changePercent24Hr) + "%"
                percentChange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_percent_up
                    )
                )
            }
            adapterFilter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.filters,
                android.R.layout.simple_spinner_item
            )
            adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            filterDropDownMenu.adapter = adapterFilter
        }
    }

    private fun drawChart(data: MutableList<DataHistory>) {
        lineList = ArrayList()
        data.forEach {
            lineList.add(Entry(it.time.toFloat(), it.priceUsd))
        }

        chart = LineChartCrypto(binding!!.lineChart, requireContext())
        chart.initLineDataSet(lineList)
        chart.setLineData()
        chart.initLineChart()
    }

    private fun initListeners() {
        binding?.run {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            filterDropDownMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        pos: Int,
                        id: Long
                    ) {
                        when (pos) {
                            TO_DAY -> {
                                getData(-3)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                            WEEK -> {
                                getData(-9)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                            MONTH -> {
                                getData(-30)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                            YEAR -> {
                                getData(-365)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    companion object {
        const val TO_DAY = 0
        const val WEEK = 1
        const val MONTH = 2
        const val YEAR = 3
    }
}