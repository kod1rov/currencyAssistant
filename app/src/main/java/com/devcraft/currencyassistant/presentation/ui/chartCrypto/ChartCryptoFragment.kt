package com.devcraft.currencyassistant.presentation.ui.chartCrypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.devcraft.currencyassistant.databinding.FragmentChartCryptoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartCryptoFragment : Fragment() {

    private var _binding: FragmentChartCryptoBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartCryptoBinding.inflate(inflater, container, false)
        return binding.root
    }



}