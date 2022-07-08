package com.devcraft.currencyassistant.presentation.ui.news_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.FragmentPostsBinding
import com.devcraft.currencyassistant.entities.Post
import dagger.hilt.android.AndroidEntryPoint

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
        initViews()
        onBackPress()
        initListeners()
        initStateListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initStateListener() {
        vm.postLiveData.observe(viewLifecycleOwner){
            println(it)
            if(it.isNotEmpty()){
                adapterNews.items = it as MutableList<Post>
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}