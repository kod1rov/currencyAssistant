package com.devcraft.currencyassistant.presentation.ui.tutorial

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.app.OnBackPressed
import com.devcraft.currencyassistant.constant.Constants
import com.devcraft.currencyassistant.databinding.FragmentTutorialBinding

class TutorialFragment : Fragment(), OnBackPressed {

    private var _binding: FragmentTutorialBinding? = null
    private val binding get() = _binding!!

    private lateinit var navigationController: NavController
    private val snapHelper: SnapHelper = PagerSnapHelper()
    private  var idTutorial: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)
        idTutorial = arguments?.getInt("idTutorial")!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.rvTutorial.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(binding.rvTutorial)
        showTutorial(idTutorial)
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            navigationController.navigate(R.id.action_tutorialFragment_to_mainFragment,null,null)
        }
    }

    private fun showTutorial(id: Int?) {
        when(id) {
            1 -> binding.rvTutorial.adapter = TutorialAdapter(Constants.tutorial1)
            2 -> binding.rvTutorial.adapter = TutorialAdapter(Constants.tutorial2)
            3 -> binding.rvTutorial.adapter = TutorialAdapter(Constants.tutorial3)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        navigationController.popBackStack()
//        navigationController.navigate(
//            R.id.action_tutorialFragment_to_mainFragment,null,null)
    }

}