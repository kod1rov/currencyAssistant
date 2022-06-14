package com.devcraft.currencyassistant.presentation.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.currencyassistant.MainActivity
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.data.remote.dto.Results
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import com.devcraft.currencyassistant.databinding.FragmentMainBinding
import com.devcraft.currencyassistant.viewModels.PostViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationController: NavController
    private val adapterNavigationArticle = NavigationTutorialAdapter()

    private val vm by viewModels<PostViewModel>()

    private val adapterNews = PostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        showBottomSheetDialog()
        initViews()
        initListeners()
        initStateListener()
        return binding.root
    }

    private fun initStateListener() {
        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collectLatest {
                    when(val state = it){
                        is NetworkResult.ApiError -> Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                        is NetworkResult.Success -> {
                            if(state.data.results.isNotEmpty()){
                                adapterNews.items = state.data.results.take(3) as MutableList<Results>
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.run {
            rvNews.adapter = adapterNews
            rvNews.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvTutorial.adapter = adapterNavigationArticle
            rvTutorial.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }
        getPosition(adapterNavigationArticle)
    }

    private fun getPosition(adapterNavigationArticle: NavigationTutorialAdapter){
        adapterNavigationArticle.setOnItemClickListener(object : NavigationTutorialAdapter.OnClickListener{
            override fun onClick(position: Int) {
                val bundleIdTutorial = bundleOf("idTutorial" to position)
                navigationController.navigate(R.id.action_mainFragment_to_tutorialFragment,
                bundleIdTutorial)
             }
        }) 
    }

    private fun initListeners() {
        binding.run {
            btnViewMore.setOnClickListener {
                navigationController.navigate(R.id.action_mainFragment_to_newsFragment)
            }
        }
    }

    private fun showBottomSheetDialog(){
        BottomSheetBehavior.from(binding.bottomSheetListCrypto).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

}