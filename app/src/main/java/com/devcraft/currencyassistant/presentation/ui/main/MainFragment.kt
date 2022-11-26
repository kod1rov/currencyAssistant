package com.devcraft.currencyassistant.presentation.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.currencyassistant.BaseFragment
import com.devcraft.currencyassistant.data.model.ChartModel
import com.devcraft.currencyassistant.databinding.FragmentMainBinding
import com.devcraft.currencyassistant.entities.Crypto
import com.devcraft.currencyassistant.entities.Post
import com.devcraft.currencyassistant.presentation.ui.news.PostViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val adapterNavigationArticle = NavigationTutorialAdapter()

    private val postVM by viewModels<PostViewModel>()
    private val currencyVM by viewModels<MainViewModel>()

    private val adapterNews = PostAdapter()
    private val adapterCurrency = CurrencyAdapter { dataC ->

        val cryptoData = ChartModel(id = dataC.id, name = dataC.name,
            priceUsd = dataC.priceUsd, changePercent24Hr = dataC.changePercent24Hr)

        navController.navigate(MainFragmentDirections.actionMainFragmentToChartCryptoFragment(cryptoData))
    }
    private val listCrypto: MutableList<Crypto> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBottomSheetDialog()
        initViews()
        initListeners()

        currencyVM.currencyLiveData.observe(viewLifecycleOwner) {
            listCrypto.clear()
            listCrypto.addAll(it)
            adapterCurrency.items = listCrypto
        }

        postVM.postLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapterNews.items = it.take(3) as MutableList<Post>
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {
        binding?.run {
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
                when (event.action) {
                    MotionEvent.AXIS_VSCROLL ->
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    MotionEvent.ACTION_DOWN ->
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    MotionEvent.ACTION_UP ->
                        v.parent.requestDisallowInterceptTouchEvent(false)
                }
                v.onTouchEvent(event)
                true
            }
        }
        getPositionNavigate(adapterNavigationArticle)
    }

    private fun getPositionNavigate(adapterNavigationArticle: NavigationTutorialAdapter) {
        adapterNavigationArticle.setOnItemClickListener(object :
            NavigationTutorialAdapter.OnClickListener {
            override fun onClick(position: Int) {
                navController.navigate(MainFragmentDirections.actionMainFragmentToTutorialFragment(position))
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListeners() {
        binding?.run {
            btnViewMore.setOnClickListener {
                navController.navigate(MainFragmentDirections.actionMainFragmentToNewsFragment())
            }

            btnSortByPrice.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    adapterCurrency.sortByPrice()
                    btnSortByPrice.rotation = 0f
                } else {
                    adapterCurrency.sortByRank()
                    btnSortByPrice.rotation = 180F
                }
            }

            fieldSearch.doAfterTextChanged { str ->
                if (str!!.isEmpty()) {
                    adapterCurrency.items = listCrypto
                } else {
                    adapterCurrency.items = listCrypto.filter {
                        it.name?.lowercase()?.contains(str) == true || it.symbol?.lowercase()
                            ?.contains(str) == true
                    } as MutableList
                }
                adapterCurrency.notifyDataSetChanged()
            }

            btnConversion.setOnClickListener {
                navController.navigate(MainFragmentDirections.actionMainFragmentToConversionFragment())
            }
        }
    }

    private fun showBottomSheetDialog() {
        binding?.let {
            BottomSheetBehavior.from(it.bottomSheetListCrypto).apply {
                peekHeight = 130
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}