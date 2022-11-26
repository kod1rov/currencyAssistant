package com.devcraft.currencyassistant.presentation.ui.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.devcraft.currencyassistant.BaseFragment
import com.devcraft.currencyassistant.constant.ArticleContent
import com.devcraft.currencyassistant.databinding.FragmentTutorialBinding
import com.devcraft.currencyassistant.presentation.ui.chartCrypto.ChartCryptoFragmentArgs
import com.devcraft.currencyassistant.utils.status.OnBackPressed

class TutorialFragment : BaseFragment<FragmentTutorialBinding>(FragmentTutorialBinding::inflate) {

    private val snapHelper: SnapHelper = PagerSnapHelper()
    private val args: TutorialFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        binding?.rvTutorial?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(binding?.rvTutorial)

        showTutorial(args.articleId)
    }

    private fun initListeners() {
        binding?.btnBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showTutorial(id: Int?) {
        when (id) {
            1 -> binding?.rvTutorial?.adapter = TutorialAdapter(ArticleContent(requireContext()).article1)
            2 -> binding?.rvTutorial?.adapter = TutorialAdapter(ArticleContent(requireContext()).article2)
            3 -> binding?.rvTutorial?.adapter = TutorialAdapter(ArticleContent(requireContext()).article3)
        }
    }
}