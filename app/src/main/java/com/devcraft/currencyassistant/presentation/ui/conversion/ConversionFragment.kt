package com.devcraft.currencyassistant.presentation.ui.conversion

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.FragmentConversionBinding
import com.devcraft.currencyassistant.entities.Crypto
import com.devcraft.currencyassistant.presentation.ui.main_fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversionFragment : Fragment() {

    private var _binding: FragmentConversionBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationController: NavController

    private val currencyVM by viewModels<MainViewModel>()
    private val listCryptoSymbols: MutableList<String> = mutableListOf()
    private val listCrypto: MutableList<Crypto> = mutableListOf()
    private lateinit var cryptoAdapter: ArrayAdapter<String>
    private var fcv = 0.0
    private var tcv = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)

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

                binding.fromCurrency.setAdapter(cryptoAdapter)
                binding.toCurrency.setAdapter(cryptoAdapter)
            }
        }

        initListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConversionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initListeners() {
        binding.run {

            btnBack.setOnClickListener {
                navigationController.navigate(R.id.action_conversionFragment_to_mainFragment)
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
            conversionCurrency(fieldFromCurrencyValue)
        }
    }


    private fun conversionCurrency(field: EditText) {
        field.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                fcv = getCurrencyValue(binding.fromCurrency.text.toString())
                tcv = getCurrencyValue(binding.toCurrency.text.toString())

                if (s.isNotEmpty() && s != "") {
                    binding.fieldToCurrencyValue.setText(
                        String.format(
                            "%.2f",
                            s.toString().toDouble() * fcv / tcv
                        )
                    )
                } else binding.fieldToCurrencyValue.setText("0,00")
            }
        })
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