package com.devcraft.currencyassistant.presentation.ui.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.devcraft.currencyassistant.constant.ArticleContent
import com.devcraft.currencyassistant.databinding.FragmentTutorialBinding
import com.devcraft.currencyassistant.utils.status.OnBackPressed

class TutorialFragment : Fragment(), OnBackPressed {

    private var _binding: FragmentTutorialBinding? = null
    private val binding get() = _binding!!

    private lateinit var navigationController: NavController
    private val snapHelper: SnapHelper = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.rvTutorial.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(binding.rvTutorial)

        showTutorial(arguments?.getInt(idArticle))
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            navigationController.popBackStack()
        }
    }

    private fun showTutorial(id: Int?) {
        when (id) {
            1 -> binding.rvTutorial.adapter = TutorialAdapter(ArticleContent(requireContext()).article1)
            2 -> binding.rvTutorial.adapter = TutorialAdapter(ArticleContent(requireContext()).article2)
            3 -> binding.rvTutorial.adapter = TutorialAdapter(ArticleContent(requireContext()).article3)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        navigationController.popBackStack()
    }

    companion object {
        const val idArticle: String = ""
    }
}