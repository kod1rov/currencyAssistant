package com.devcraft.currencyassistant.presentation.ui.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.FragmentMainBinding
import com.devcraft.currencyassistant.entities.Crypto
import com.devcraft.currencyassistant.entities.Post
import com.devcraft.currencyassistant.presentation.ui.chart_crypto.ChartCryptoFragment
import com.devcraft.currencyassistant.presentation.ui.news_fragment.PostViewModel
import com.devcraft.currencyassistant.presentation.ui.tutorial.TutorialFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationController: NavController

    private val adapterNavigationArticle = NavigationTutorialAdapter()

    private val postVM by viewModels<PostViewModel>()
    private val currencyVM by viewModels<MainViewModel>()

    private val adapterNews = PostAdapter()
    private val adapterCurrency = CurrencyAdapter()
    private val listCrypto: MutableList<Crypto> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)

        showBottomSheetDialog()
        initViews()
        initListeners()

        currencyVM.currencyLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                it.forEach { crypto ->
                    listCrypto.add(crypto)
                }
                adapterCurrency.items = listCrypto
            }
        }

        postVM.postLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapterNews.items = it.take(3) as MutableList<Post>
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {

        binding.run {
            rvNews.adapter = adapterNews
            rvNews.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvTutorial.adapter = adapterNavigationArticle
            rvTutorial.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvCryptoList.adapter = adapterCurrency
            rvCryptoList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvCryptoList.setOnTouchListener { v, event ->
                bottomSheetListCrypto.isNestedScrollingEnabled = true
                when (event.action) {
                    MotionEvent.ACTION_DOWN ->
                        v.parent.requestDisallowInterceptTouchEvent(true)
                    MotionEvent.ACTION_UP ->
                        v.parent.requestDisallowInterceptTouchEvent(false)
                }
                v.onTouchEvent(event)
                true
            }
        }
        getPositionNavigate(adapterNavigationArticle)
        getPositionCurrency(adapterCurrency)
    }

    private fun getPositionNavigate(adapterNavigationArticle: NavigationTutorialAdapter) {
        adapterNavigationArticle.setOnItemClickListener(object :
            NavigationTutorialAdapter.OnClickListener {
            override fun onClick(position: Int) {
                navigationController.navigate(
                    R.id.action_mainFragment_to_tutorialFragment,
                    bundleOf(TutorialFragment.idArticle to position)
                )
            }
        })
    }

    private fun getPositionCurrency(adapter: CurrencyAdapter) {
        adapter.setOnItemClickListener(object : CurrencyAdapter.OnClickListener {
            override fun onClick(dataC: Crypto) {
                navigationController.navigate(
                    R.id.action_mainFragment_to_chartCryptoFragment,
                    bundleOf(
                        ChartCryptoFragment.dataC to "${dataC.id}, ${dataC.name}, ${dataC.priceUsd}, ${dataC.changePercent24Hr}"
                    )
                )
            }
        })
    }

    private fun initListeners() {
        binding.run {

            btnViewMore.setOnClickListener {
                navigationController.navigate(R.id.action_mainFragment_to_newsFragment)
            }

            btnSortByPrice.setOnClickListener {
                adapterCurrency.sortByPrice()
            }

            searchCrypto(fieldSearch)

            btnConversion.setOnClickListener {
                navigationController.navigate(R.id.action_mainFragment_to_conversionFragment)
            }
        }
    }

    private fun searchCrypto(field: EditText) {
        field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = field.text
                if (str?.length == 0) {
                    adapterCurrency.items = listCrypto
                } else {
                    adapterCurrency.items = listCrypto.filter {
                        it.name!!.startsWith(s.toString(), true) || it.name.contains(s.toString(), true)
                    } as MutableList
                }
                adapterCurrency.notifyDataSetChanged()
            }
        })
    }

    private fun showBottomSheetDialog() {
        BottomSheetBehavior.from(binding.bottomSheetListCrypto).apply {
            peekHeight = 130
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}