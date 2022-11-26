package com.devcraft.currencyassistant.presentation.ui.conversion

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.devcraft.currencyassistant.BaseFragment
import com.devcraft.currencyassistant.MainActivity
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.FragmentConversionBinding
import com.devcraft.currencyassistant.entities.Crypto
import com.devcraft.currencyassistant.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversionFragment : BaseFragment<FragmentConversionBinding>(FragmentConversionBinding::inflate) {

    private val currencyVM by viewModels<MainViewModel>()
    private val listCryptoSymbols: MutableList<String> = mutableListOf()
    private val listCrypto: MutableList<Crypto> = mutableListOf()
    private lateinit var cryptoAdapter: ArrayAdapter<String>
    private var fcv = 0.0
    private var tcv = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.fieldFromCurrencyValue?.requestFocus()
        requireActivity().showKeyboard()

        getData()
        initListeners()
    }

    private fun getData() {
        currencyVM.currencyLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                it.forEach { crypto ->
                    listCryptoSymbols.add(crypto.symbol.toString())
                }
                it.forEach { crypto ->
                    listCrypto.add(crypto)
                }
                cryptoAdapter = ArrayAdapter<String>(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    listCryptoSymbols
                )

                binding?.fromCurrency?.setAdapter(cryptoAdapter)
                binding?.toCurrency?.setAdapter(cryptoAdapter)
            }
        }
    }

    private fun initListeners() {
        binding?.run {
            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnChange.setOnClickListener {
                val animScale =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.exchange_stroke_btn)
                it.startAnimation(animScale)

                val fc = fromCurrency.text
                val tc = toCurrency.text

                if (fromCurrency.text != toCurrency.text) {
                    fromCurrency.text = tc
                    toCurrency.text = fc
                }
            }
            fieldFromCurrencyValue.doAfterTextChanged { str ->
                fcv = getCurrencyValue(binding?.fromCurrency?.text.toString())
                tcv = getCurrencyValue(binding?.toCurrency?.text.toString())

                if (str.toString() != "") {
                    binding?.fieldToCurrencyValue?.text = String.format(
                        "%.2f",
                        str.toString().toDouble() * fcv / tcv
                    )
                    } else binding?.fieldToCurrencyValue?.setText("0,00")
                }
        }
    }

    private fun getCurrencyValue(value: String): Double {
        var tcv = 0.0
        listCrypto.forEach { crypto ->
            if (value == crypto.symbol) {
                tcv = crypto.priceUsd!!
            }
        }
        return tcv
    }
}