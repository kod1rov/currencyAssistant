package com.devcraft.currencyassistant.presentation.ui.chart_crypto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.data.remote.dto.DataHistory
import com.devcraft.currencyassistant.databinding.FragmentChartCryptoBinding
import com.devcraft.currencyassistant.utils.status.OnBackPressed
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartCryptoFragment : Fragment(), OnBackPressed {

    private var _binding: FragmentChartCryptoBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationController: NavController

    private val vm by viewModels<ChartViewModel>()
    private lateinit var lineList: ArrayList<Entry>
    private lateinit var chart: LineChartCrypto
    private lateinit var list: List<String>

    private lateinit var adapterFilter: ArrayAdapter<CharSequence>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        list = arguments?.getString(dataC)!!.split(",")

        initViews()
        initListeners()
    }

    private fun getData(filter: Int) {
        vm.getHistoryCrypto(list[0], filter)

        vm.historyLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                drawChart(it)
            }
        }
    }

    private fun initViews() {
        binding.run {
            nameCurrency.text = list[1]
            valueCurrency.text = "$" + String.format("%.2f", list[2].toDouble())

            if (list[3].toDouble() < 0.0) {
                percentChange.text = String.format("%.2f", list[3].toDouble()) + "%"
                percentChange.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_percent_down
                    )
                )
            } else {
                percentChange.text =
                    "+" + String.format("%.2f", list[3].toDouble()) + "%"
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

        chart = LineChartCrypto(binding.lineChart, requireContext())
        chart.initLineDataSet(lineList)
        chart.setLineData()
        chart.initLineChart()
    }

    private fun initListeners() {
        binding.run {
            btnBack.setOnClickListener {
                navigationController.popBackStack()
            }
            filterDropDownMenu.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        pos: Int,
                        id: Long
                    ) {
                        when (pos) {
                            0 -> {
                                getData(-3)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                            1 -> {
                                getData(-9)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                            2 -> {
                                getData(-30)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                            3 -> {
                                getData(-365)
                                filterTV.text = parent.getItemAtPosition(pos).toString()
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartCryptoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        navigationController.popBackStack()
    }

    companion object {
        const val dataC: String = ""
    }

}