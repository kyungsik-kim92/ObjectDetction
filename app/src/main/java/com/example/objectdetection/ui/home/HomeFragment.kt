package com.example.objectdetection.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.objectdetection.R
import com.example.objectdetection.base.BaseFragment
import com.example.objectdetection.base.ViewEvent
import com.example.objectdetection.base.ViewState
import com.example.objectdetection.databinding.FragmentHomeBinding
import com.example.objectdetection.ui.adapter.FragmentPagerAdapter
import com.example.objectdetection.ui.bookmark.BookmarkFragment
import com.example.objectdetection.ui.mypage.MyPageFragment
import com.example.objectdetection.ui.search.SearchFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()

    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_home)[position]
            tab.icon = resources.obtainTypedArray(R.array.array_home_icon).getDrawable(position)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun initUi() {
        val list = listOf(
            SearchFragment(),
            BookmarkFragment(),
            MyPageFragment()
        )
        val adapter = FragmentPagerAdapter(list, this)

        with(binding) {
            viewpager.adapter = adapter
            viewpager.isUserInputEnabled = false
            viewpager.offscreenPageLimit = list.size
            TabLayoutMediator(tab, viewpager, tabConfigurationStrategy).attach()
        }
        viewModel.viewEvent.map(::onChangeViewEvent).launchIn(lifecycleScope)
    }

    override fun onChangedViewState(state: ViewState) {}

    override fun onChangeViewEvent(event: ViewEvent) {}


}

