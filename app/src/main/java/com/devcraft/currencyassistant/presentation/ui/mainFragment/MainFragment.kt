package com.devcraft.currencyassistant.presentation.ui.mainFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.FragmentMainBinding
import com.devcraft.currencyassistant.entities.Post
import com.devcraft.currencyassistant.presentation.ui.newsFragment.PostViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)

        currencyVM.currencyLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapterCurrency.items = it
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
        showBottomSheetDialog()
        initViews()
        initListeners()
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
                    MotionEvent.ACTION_DOWN ->                         // Disallow NestedScrollView to intercept touch events.
                        v.parent.requestDisallowInterceptTouchEvent(true)
                    MotionEvent.ACTION_UP ->                         // Allow NestedScrollView to intercept touch events.
                        v.parent.requestDisallowInterceptTouchEvent(false)
                }
                // Handle RecyclerView touch events.
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
                val bundleIdTutorial = bundleOf("idTutorial" to position)
                navigationController.navigate(
                    R.id.action_mainFragment_to_tutorialFragment,
                    bundleIdTutorial
                )
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

    private fun showBottomSheetDialog() {
        BottomSheetBehavior.from(binding.bottomSheetListCrypto).apply {
            peekHeight = 130
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

}