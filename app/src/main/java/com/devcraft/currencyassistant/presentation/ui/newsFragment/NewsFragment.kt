package com.devcraft.currencyassistant.presentation.ui.newsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import com.devcraft.currencyassistant.databinding.FragmentPostsBinding
import com.devcraft.currencyassistant.viewModels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding : FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationController: NavController

    private val vm by viewModels<PostViewModel>()
    private val adapterNews = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        initViews()
        onBackPress()
        initListeners()
        initStateListener()
        return binding.root
    }

    private fun initStateListener() {
        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collectLatest {
                    when(val state = it){
                        is NetworkResult.ApiError -> Toast.makeText(context,"Error", Toast.LENGTH_LONG).show()
                        is NetworkResult.Success -> {
                            if(state.data.results.isNotEmpty()){
                                adapterNews.items = state.data.results
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.rvNews.adapter = adapterNews
        binding.rvNews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun initListeners() {
            binding.run {
                btnBack.setOnClickListener {
                    navigationController.navigate(
                        R.id.action_newsFragment_to_mainFragment)
                }
            }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigationController.navigate(
                        R.id.action_newsFragment_to_mainFragment,null,null)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}